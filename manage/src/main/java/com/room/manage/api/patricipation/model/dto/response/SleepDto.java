package com.room.manage.api.patricipation.model.dto.response;

import com.room.manage.api.patricipation.model.entity.Sleep;
import com.room.manage.api.patricipation.model.entity.SleepReason;
import com.room.manage.core.util.DateUtil;

public class SleepDto {

    String startTime;
    String finishTime;
    SleepReason sleepReason;

    public SleepDto(Sleep sleep){
        this.startTime = DateUtil.formatToString(sleep.getSleepStartTime());
        this.finishTime = DateUtil.formatToString(sleep.getSleepFinishTime());
        this.sleepReason = sleep.getReason();
    }
}
