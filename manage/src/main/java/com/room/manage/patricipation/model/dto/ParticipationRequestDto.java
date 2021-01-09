package com.room.manage.patricipation.model.dto;

import com.room.manage.room.model.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParticipationRequestDto{
    Long userId;
    String floor;
    String field;
}
