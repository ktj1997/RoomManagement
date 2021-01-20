package com.room.manage.api.room.service;

import com.room.manage.api.room.model.dto.RoomInfoDto;

public interface RoomService {
    RoomInfoDto getRoomInfo(String field, String floor);
}
