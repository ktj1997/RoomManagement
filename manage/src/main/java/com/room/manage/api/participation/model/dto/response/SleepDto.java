package com.room.manage.api.participation.model.dto.response;

import com.room.manage.api.participation.model.entity.Sleep;
import com.room.manage.api.participation.model.entity.SleepReason;
import com.room.manage.core.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@ApiModel("Sleep 반환 Dto")
@AllArgsConstructor
public class SleepDto {

    @ApiModelProperty("부재 시작시간")
    String startTime;
    @ApiModelProperty("부재 종료시간")
    String finishTime;
    @ApiModelProperty("부재 사유")
    SleepReason sleepReason;

    public SleepDto(Sleep sleep){
        this.startTime = DateUtil.formatToString(sleep.getSleepStartTime());
        this.finishTime = DateUtil.formatToString(sleep.getSleepFinishTime());
        this.sleepReason = sleep.getReason();
    }
}
