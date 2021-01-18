package com.room.manage.patricipation.service;

import com.room.manage.notice.Notice;
import com.room.manage.notice.NoticeService;
import com.room.manage.patricipation.exception.*;
import com.room.manage.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.patricipation.model.dto.response.SleepInfoResponseDto;
import com.room.manage.patricipation.model.dto.request.SleepRequestDto;
import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.model.entity.ParticipationType;
import com.room.manage.patricipation.model.entity.Sleep;
import com.room.manage.patricipation.repository.ParticipationRepository;
import com.room.manage.room.exception.AlreadyMaximumParticipantException;
import com.room.manage.room.exception.RoomNotExistException;
import com.room.manage.room.model.entity.Room;
import com.room.manage.room.model.entity.RoomType;
import com.room.manage.room.model.entity.Status;
import com.room.manage.room.repository.RoomRepository;
import com.room.manage.user.exception.UserNotExistException;
import com.room.manage.user.model.entity.User;
import com.room.manage.user.repository.UserRepository;
import com.room.manage.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationServiceImpl implements ParticipationService{

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;
    private final NoticeService noticeService;

    @Override
    public ParticipationResponseDto joinRoom(ParticipationRequestDto participationRequestDto) {
        Room room = roomRepository.findByFloorAndField(participationRequestDto.getFloor(), participationRequestDto.getField())
                .orElseThrow(RoomNotExistException::new);
        User user = userRepository.findById(participationRequestDto.getUserId())
                .orElseThrow(UserNotExistException::new);
        Participation participation;

        if(!DateUtil.checkValidDate(participationRequestDto.getFinishTime()))
            throw new InvalidTimeRequestException();

        if(participationRepository.existsByParticipant(user))
            throw new AlreadyParticipateException();

        if(room.canJoin())
        {
            if(room.getStatus().equals(RoomType.GROUP) && room.getStatus().equals(Status.EMPTY))
                room.setDelegate(user);
            participation = Participation.builder()
                    .user(user)
                    .finishTime(DateUtil.formatToDate(participationRequestDto.getFinishTime()))
                    .type(ParticipationType.ACTIVE)
                    .room(room).build();
            participationRepository.save(participation);
            noticeService.noticeOthers(room.getFloor(), room.getField(),user.getUserName());
            room.join();
        }else
                throw new AlreadyMaximumParticipantException();
        return new ParticipationResponseDto(participation);
    }

    /**
     * 퇴실
     *
     * @param userId
     */
    @Override
    public void exitRoom(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoSuchParticipationException::new);
        Room room = participation.getRoom();

        room.exit();
        noticeService.deleteEmitter(room.getFloor(),room.getField(),user.getId());
        participationRepository.delete(participation);
    }

    /**
     * Sleep 상태로 바꿔준다.
     * 만약 Room의 모든 인원이 Sleep이라면 Room의 상태 또한 Sleep으로 변경한다.
     * 부재시간은 이용종료시간을 넘어서 설정할 수 없다.
     * @param sleepRequestDto
     */
    @Override
    public SleepInfoResponseDto toSleepStatus(Long id,SleepRequestDto sleepRequestDto) {
        User user = userRepository.findById(id).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoSuchParticipationException::new);
        Room room = participation.getRoom();

        if(participation.getRemainSleepNum()>0){
            if(participation.getSleep() == null){
                if(DateUtil.formatToDate(sleepRequestDto.getDate()).before(participation.getFinishTime()))
                {
                    room.setSleepNum(room.getSleepNum() + 1);
                    if(room.getSleepNum() == 0)
                        room.setStatus(Status.SLEEP);
                    participation.setSleep(new Sleep(new Date(),DateUtil.formatToDate(sleepRequestDto.getDate()),sleepRequestDto.getReason()));

                    return new SleepInfoResponseDto(participation);
                }else
                    throw new SleepTimeCannotExceedFinishTimeException();
            }else
                throw new AlreadySleepStatusException();
        }else
            throw new SleepRequestDenyException();
    }

    /**
     * 현재 참여하고 있을 때, 시간 추가
     * @param extendTimeRequestDto
     */
    @Override
    public Date extendTime(Long userId,ExtendTimeRequestDto extendTimeRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoSuchParticipationException::new);

        participation.setFinishTime(DateUtil.formatToDate(extendTimeRequestDto.getFinishTime()));
        return participation.getFinishTime();
    }
}
