package com.room.manage.api.room.service;

import com.room.manage.api.room.exception.RoomNotExistException;
import com.room.manage.api.room.model.dto.DetailRoomInfoDto;
import com.room.manage.api.room.model.dto.SimpleRoomInfoDto;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    /**
     * 해당 Room의 정보
     *
     * @param floor Room식별자 floor
     * @param field Rom 식별자 field
     * @return RoomInfoDto
     */
    @Override
    public DetailRoomInfoDto getRoomInfo(String floor, String field) {
        Room room = roomRepository.findByFieldAndFloor(field, floor).orElseThrow(RoomNotExistException::new);
        return new DetailRoomInfoDto(room);
    }

    @Override
    public List<SimpleRoomInfoDto> getAllRoomsInfo() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(it -> new SimpleRoomInfoDto(it)).collect(Collectors.toList());
    }


}
