package com.room.manage.patricipation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExtendTimeRequestDto {
    Long userId;
    int hour;
    int minute;
}
