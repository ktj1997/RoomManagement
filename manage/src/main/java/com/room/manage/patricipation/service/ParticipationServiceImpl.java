package com.room.manage.patricipation.service;

import com.room.manage.patricipation.exception.RoomTypeNotMatchedException;
import com.room.manage.patricipation.model.dto.ParticipationRequestDto;
import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.repository.ParticipationRepository;
import com.room.manage.room.exception.AlreadyMaximumParticipantException;
import com.room.manage.room.exception.RoomNotExistException;
import com.room.manage.room.model.entity.Room;
import com.room.manage.room.model.entity.RoomType;
import com.room.manage.room.repository.RoomRepository;
import com.room.manage.user.exception.UserNotExistException;
import com.room.manage.user.model.entity.User;
import com.room.manage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
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

        if(participationRequestDto.getRoomType() != room.getType())
            throw new RoomTypeNotMatchedException();

        if((participationRequestDto.getRoomType() == RoomType.GROUP)){
            if(room.canJoin(participationRequestDto.getParticipantsNum())){
                Participation participation = Participation.builder()
                        .user(user)
                        .finishTime(new Date())
                        .room(room)
                        .build();
                room.join(participationRequestDto.getParticipantsNum());
                participationRepository.save(participation);
            }else
                throw new AlreadyMaximumParticipantException();
        }else{
            if(room.canJoin()){
                Participation participation = Participation.builder()
                        .user(user)
                        .finishTime(new Date())
                        .room(room)
                        .build();
                room.join();
                participationRepository.save(participation);
            }else
                throw new AlreadyMaximumParticipantException();
        }
    }
}
