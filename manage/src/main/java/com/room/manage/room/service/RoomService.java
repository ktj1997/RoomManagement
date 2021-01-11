package com.room.manage.room.service;

import com.room.manage.room.model.dto.RoomInfoDto;

public interface RoomService {
    RoomInfoDto getRoomInfo(String field, String floor);
}
