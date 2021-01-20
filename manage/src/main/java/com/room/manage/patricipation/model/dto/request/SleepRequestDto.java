package com.room.manage.patricipation.model.dto.request;

import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.model.entity.SleepReason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@ApiModel
@Getter
@AllArgsConstructor
public class SleepRequestDto {
    @ApiModelProperty("부재 종료시간 (yyyy-MM-dd-HH:mm) mm은 10분단위로 (0,10,20,30,40,50)")
    String date;
    @ApiModelProperty("부재 이유")
    SleepReason reason;
}
