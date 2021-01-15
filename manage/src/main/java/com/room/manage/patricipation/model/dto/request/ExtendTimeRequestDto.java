package com.room.manage.patricipation.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExtendTimeRequestDto {
    /**
     * 분은 30분단위 (ex: hour:0~24, minute:0 or 30)
     */
    String finishTime;
}
