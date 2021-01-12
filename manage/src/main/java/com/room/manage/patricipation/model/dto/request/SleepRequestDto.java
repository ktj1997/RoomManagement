package com.room.manage.patricipation.model.dto.request;

import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.model.entity.SleepReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class SleepRequestDto {
    String date;
    SleepReason reason;
}
