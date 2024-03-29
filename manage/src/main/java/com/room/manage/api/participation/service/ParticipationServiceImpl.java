package com.room.manage.api.participation.service;

import com.room.manage.api.participation.event.ExitSuccessEvent;
import com.room.manage.api.participation.event.joinSuccessEvent;
import com.room.manage.api.participation.event.SleepFinishedEvent;
import com.room.manage.api.participation.event.SleepSuccessEvent;
import com.room.manage.api.participation.exception.*;
import com.room.manage.api.participation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.dto.request.SleepRequestDto;
import com.room.manage.api.participation.model.dto.response.ExitResponseDto;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.participation.model.entity.AlarmType;
import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.api.participation.model.entity.ParticipationStatus;
import com.room.manage.api.participation.model.entity.Sleep;
import com.room.manage.api.participation.repository.ParticipationRepository;
import com.room.manage.api.participation.repository.SleepRepository;
import com.room.manage.api.participation.service.cache.ParticipationCacheFunction;
import com.room.manage.api.room.exception.RoomNotExistException;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.room.model.entity.RoomId;
import com.room.manage.api.room.repository.RoomRepository;
import com.room.manage.api.user.exception.UserNotExistException;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.repository.UserRepository;
import com.room.manage.core.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;
    private final SleepRepository sleepRepository;
    private final ParticipationCacheFunction participationCacheFunction;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    @Transactional(noRollbackFor = AlarmExecutionException.class)
    public ParticipationResponseDto joinRoom(Long userId, ParticipationRequestDto participationRequestDto, String token) {
        Room room = roomRepository.findById(new RoomId(participationRequestDto.getFloor(), participationRequestDto.getField()))
                .orElseThrow(RoomNotExistException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotExistException::new);
        Participation participation;

        if (!DateUtil.checkRequestDateIsNotPastAndValid(participationRequestDto.getFinishTime()))
            throw new InvalidTimeRequestException();

        else if (participationRepository.existsByParticipant(user))
            throw new AlreadyParticipateException();

        else if (!room.canJoin())
            throw new AlreadyMaximumParticipantException();
        else {
            if (token != null)
                user.setFcmToken(token);
            participation = Participation.builder()
                    .user(user)
                    .finishTime(DateUtil.formatToDate(participationRequestDto.getFinishTime()))
                    .type(ParticipationStatus.ACTIVE)
                    .room(room).build();
            participationRepository.save(participation);
            eventPublisher.publishEvent(new joinSuccessEvent(room, user, AlarmType.JOIN, participation));
        }
        return new ParticipationResponseDto(participation);
    }

    /**
     * 퇴실
     */
    @Override
    @Transactional(noRollbackFor = AlarmExecutionException.class)
    public ExitResponseDto exitRoom(Long userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
            Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoParticipationException::new);
            Room room = participation.getRoom();

            participationCacheFunction.removeParticipationCache(participation.getId());
            participationRepository.delete(participation);
            eventPublisher.publishEvent(new ExitSuccessEvent(room, user, AlarmType.EXIT, participation));
            return new ExitResponseDto(participation);
        } catch (ResourceAccessException e) {
            throw new AlarmExecutionException();
        }
    }

    /**
     * Sleep 상태로 바꿔준다.
     * 만약 Room의 모든 인원이 Sleep이라면 Room의 상태 또한 Sleep으로 변경한다.
     * 부재시간은 이용종료시간을 넘어서 설정할 수 없다.
     *
     * @param sleepRequestDto
     */
    @Override
    public ParticipationResponseDto toSleepStatus(Long userId, SleepRequestDto sleepRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoParticipationException::new);
        Room room = participation.getRoom();

        if (participation.getRemainSleepNum() > 0) {
            if (participation.getSleep() == null) {
                if (DateUtil.formatToDate(sleepRequestDto.getDate()).before(participation.getFinishTime())) {
                    Sleep sleep = new Sleep(new Date(), DateUtil.formatToDate(sleepRequestDto.getDate()), sleepRequestDto.getReason(), participation);
                    sleepRepository.save(sleep);
                    eventPublisher.publishEvent(new SleepSuccessEvent(room, participation, sleep));
                    return new ParticipationResponseDto(participation, sleep);
                } else
                    throw new InvalidTimeRequestException();
            } else
                throw new AlreadySleepStatusException();
        } else
            throw new RemainSleepNumZeroException();
    }

    /**
     * 부재 상태 --> 활동상태
     */
    @Override
    public ParticipationResponseDto toActiveStatus(Sleep sleep) {
        Participation participation = sleep.getParticipation();
        Room room = participation.getRoom();
        sleepRepository.delete(sleep);
        eventPublisher.publishEvent(new SleepFinishedEvent(room, participation));
        return new ParticipationResponseDto(participation);
    }

    /**
     * 현재 참여하고 있을 때, 시간 추가
     *
     * @param extendTimeRequestDto
     */
    @Override
    public ParticipationResponseDto extendTime(Long userId, ExtendTimeRequestDto extendTimeRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoParticipationException::new);

        if (DateUtil.checkRequestDateIsNotPastAndValid(extendTimeRequestDto.getFinishTime())
                && DateUtil.checkExtendRequestTimeIsAfterThanFinishTime(extendTimeRequestDto.getFinishTime(), participation.getFinishTime())) {
            participation.setFinishTime(DateUtil.formatToDate(extendTimeRequestDto.getFinishTime()));
            return new ParticipationResponseDto(participation);
        } else
            throw new InvalidTimeRequestException();
    }
}
