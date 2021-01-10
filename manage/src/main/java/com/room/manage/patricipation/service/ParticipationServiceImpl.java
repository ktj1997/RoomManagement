package com.room.manage.patricipation.service;

import com.room.manage.patricipation.exception.NoSuchParticipationException;
import com.room.manage.patricipation.exception.RoomTypeNotMatchedException;
import com.room.manage.patricipation.exception.SleepRequestDenyException;
import com.room.manage.patricipation.model.dto.ParticipationRequestDto;
import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.model.entity.ParticipationType;
import com.room.manage.patricipation.model.entity.Sleep;
import com.room.manage.patricipation.model.entity.SleepReason;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationServiceImpl implements ParticipationService{

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;

    @Override
    public void joinRoom(ParticipationRequestDto participationRequestDto) {
        Room room = roomRepository.findByFloorAndField(participationRequestDto.getFloor(), participationRequestDto.getField())
                .orElseThrow(RoomNotExistException::new);
        User user = userRepository.findById(participationRequestDto.getUserId())
                .orElseThrow(UserNotExistException::new);

        if(room.getType() == RoomType.GROUP){
                if(room.getStatus() == Status.EMPTY){
                    room.setDelegate(user);
                    Participation participation = Participation.builder()
                            .user(user)
                            .finishTime(new Date())
                            .type(ParticipationType.ACTIVE)
                            .room(room).build();
                    participationRepository.save(participation);
                }else{
                    /**
                     * 참가요청 만들기 --> 성공시 들여보내고, 아니면 거부
                     * SSE공부하자
                     */
                    System.out.format("%s 님 %s 님이 참여요청 중입니다. 승낙 하시겠습니까? (Y/N)"
                            ,room.getDelegate().getName(),user.getName());
                    if(true){
                        Participation participation = Participation.builder()
                                .user(user)
                                .finishTime(new Date())
                                .type(ParticipationType.ACTIVE)
                                .room(room).build();
                        participationRepository.save(participation);
                    }else{

                    }
                }
        } else{
            if(room.getNowNum()+1 <= room.getMaxNum()){
                if(room.getStatus() == Status.EMPTY)
                    room.setStatus(Status.ACTIVATE);

                List<Participation> otherParticipates = participationRepository
                        .findAllByRoom_FloorAndRoom_Floor(participationRequestDto.getFloor(),participationRequestDto.getField());

                Participation participation = Participation.builder()
                        .user(user)
                        .finishTime(new Date())
                        .type(ParticipationType.ACTIVE)
                        .room(room).build();
                participationRepository.save(participation);

                /**
                 *  개인방의 모든 인원들에게 알림 보내기.
                 *  SSE 공부하자
                 */
                otherParticipates.stream().forEach((it) -> System.out.format("%s 님! %s 님이 참여할 예정입니다. 자리를 정돈해주세요!",
                        it.getParticipant().getName(),user.getName()));
            }else
                throw new AlreadyMaximumParticipantException();
        }
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
        participationRepository.delete(participation);

    }

    /**
     * Sleep 상태로 바꿔준다.
     * 만약 Room의 모든 인원이 Sleep이라면 Room의 상태 또한 Sleep으로 변경한다.
     * @param userId
     * @param sleepReason
     */
    @Override
    public void toSleepStatus(Long userId, SleepReason sleepReason) {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoSuchParticipationException::new);
        Room room = participation.getRoom();

        if(participation.getRemainSleepNum()>0){
            room.setSleepNum(room.getSleepNum() + 1);
            if(room.getSleepNum() == 0)
                room.setStatus(Status.SLEEP);
            participation.setSleep(new Sleep(new Date(),sleepReason));
        }else
            throw new SleepRequestDenyException();
    }
}
