package com.room.manage.patricipation.model.dto.request;

import com.room.manage.room.model.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParticipationRequestDto{
    Long userId;
    String floor;
    String field;
    /**
     * 분은 30분단위 (ex: hour:0~24, minute:0 or 30)
     */
    String finishTime;
}
