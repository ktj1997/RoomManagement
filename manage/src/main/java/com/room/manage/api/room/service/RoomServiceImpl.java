package com.room.manage.api.room.service;

import com.room.manage.api.patricipation.repository.ParticipationRepository;
import com.room.manage.api.room.exception.RoomNotExistException;
import com.room.manage.api.room.model.dto.RoomInfoDto;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.room.model.entity.RoomId;
import com.room.manage.api.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;

    /**
     * 해당 Room의 정보
     * @param floor Room식별자 floor
     * @param field Rom 식별자 field
     * @return RoomInfoDto
     */
    @Override
    public RoomInfoDto getRoomInfo(String floor,String field) {
        RoomId roomId = new RoomId(floor,field);
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotExistException::new);
        return new RoomInfoDto(room);
    }
}
