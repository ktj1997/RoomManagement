package com.room.manage.patricipation.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParticipationRequestDto{
    Long userId;
    String floor;
    String field;
    /**
     * yyyy-MM-dd-HH:mm
     * 분은 10분단위 (ex: hour:0~24, minute:0 or 30)
     */
    String finishTime;
}
