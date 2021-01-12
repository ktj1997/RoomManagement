package com.room.manage.patricipation.model.dto.response;

import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.model.entity.SleepReason;
import com.room.manage.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SleepInfoResponseDto
{
    SleepReason reason;
    String startTime;
    String finishTime;
    int remainSleepNum;

    public SleepInfoResponseDto(Participation participation)
    {
        this.reason = participation.getSleep().getReason();
        this.startTime = DateUtil.formatToString(participation.getStartTime());
        this.finishTime = DateUtil.formatToString(participation.getFinishTime());
        this.remainSleepNum = participation.getRemainSleepNum();
    }
}
