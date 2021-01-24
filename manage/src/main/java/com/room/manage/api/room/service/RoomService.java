package com.room.manage.api.room.service;

import com.room.manage.api.room.model.dto.DetailRoomInfoDto;
import com.room.manage.api.room.model.dto.SimpleRoomInfoDto;

import java.util.List;

public interface RoomService {
    DetailRoomInfoDto getRoomInfo(String field, String floor);
    List<SimpleRoomInfoDto> getAllRoomInfo();
}
