package com.room.manage.api.patricipation.service;

import com.room.manage.api.patricipation.exception.*;
import com.room.manage.api.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.patricipation.model.dto.request.SleepRequestDto;
import com.room.manage.api.patricipation.model.entity.Participation;
import com.room.manage.api.patricipation.model.entity.ParticipationType;
import com.room.manage.api.patricipation.model.entity.Sleep;
import com.room.manage.api.patricipation.repository.ParticipationRepository;
import com.room.manage.api.patricipation.exception.AlreadyMaximumParticipantException;
import com.room.manage.api.patricipation.repository.SleepRepository;
import com.room.manage.api.room.exception.RoomNotExistException;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.room.repository.RoomRepository;
import com.room.manage.api.user.exception.UserNotExistException;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.repository.UserRepository;
import com.room.manage.core.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationServiceImpl implements ParticipationService{

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;
    private final SleepRepository sleepRepository;
    private final RestTemplate restTemplate;

    @Value("${application.socket.url}")
    String socketURL;

    @Override
    public ParticipationResponseDto joinRoom(ParticipationRequestDto participationRequestDto) {
        Room room = roomRepository.findByFloorAndField(participationRequestDto.getFloor(), participationRequestDto.getField())
                .orElseThrow(RoomNotExistException::new);
        User user = userRepository.findById(Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName()))
                .orElseThrow(UserNotExistException::new);
        Participation participation;

        if(!DateUtil.checkValidDate(participationRequestDto.getFinishTime()))
            throw new InvalidTimeRequestException();

        else if(participationRepository.existsByParticipant(user))
            throw new AlreadyParticipateException();

        else if(!room.canJoin())
            throw new AlreadyMaximumParticipantException();
        else {
            participation = Participation.builder()
                    .user(user)
                    .finishTime(DateUtil.formatToDate(participationRequestDto.getFinishTime()))
                    .type(ParticipationType.ACTIVE)
                    .room(room).build();
            participationRepository.save(participation);
            room.join();
        }
        return new ParticipationResponseDto(participation);
    }

    /**
     * 퇴실
     */
    @Override
    public void exitRoom(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoParticipationException::new);
        Room room = participation.getRoom();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> params = new LinkedHashMap<>();
        participationRepository.delete(participation);
        room.exit();

        params.put("userId",user.getId().toString());
        params.put("userName",user.getName());
        params.put("floor",room.getFloor());
        params.put("field",room.getField());



        if(restTemplate.exchange(socketURL, HttpMethod.DELETE,new HttpEntity(params,headers),boolean.class).getStatusCode() != HttpStatus.OK)
            throw new ConnectionClosedException();
    }

    /**
     * Sleep 상태로 바꿔준다.
     * 만약 Room의 모든 인원이 Sleep이라면 Room의 상태 또한 Sleep으로 변경한다.
     * 부재시간은 이용종료시간을 넘어서 설정할 수 없다.
     * @param sleepRequestDto
     */
    @Override
    public ParticipationResponseDto toSleepStatus(SleepRequestDto sleepRequestDto) {
        User user = userRepository.findById(Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoParticipationException::new);
        Room room = participation.getRoom();

        if(participation.getRemainSleepNum()>0){
            if(participation.getSleep() == null){
                if(DateUtil.formatToDate(sleepRequestDto.getDate()).before(participation.getFinishTime()))
                {
                    room.setSleepNum(room.getSleepNum() + 1);
                    participation.setParticipationType(ParticipationType.SLEEP);
                    sleepRepository.save(new Sleep(new Date(),DateUtil.formatToDate(sleepRequestDto.getDate()),sleepRequestDto.getReason(),participation));
                    return new ParticipationResponseDto(participation);
                }else
                    throw new InvalidTimeRequestException();
            }else
                throw new AlreadySleepStatusException();
        }else
            throw new RemainSleepNumZeroException();
    }

    /**
     * 부재 상태 --> 활동상태
     */
    @Override
    public void toActiveStatus(Sleep sleep) {
        Participation participation = sleep.getParticipation();
        participation.setParticipationType(ParticipationType.ACTIVE);

        sleepRepository.delete(sleep);
    }

    /**
     * 현재 참여하고 있을 때, 시간 추가
     * @param extendTimeRequestDto
     */
    @Override
    public Date extendTime(ExtendTimeRequestDto extendTimeRequestDto) {
        User user = userRepository.findById(Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoParticipationException::new);

        participation.setFinishTime(DateUtil.formatToDate(extendTimeRequestDto.getFinishTime()));
        return participation.getFinishTime();
    }
}
