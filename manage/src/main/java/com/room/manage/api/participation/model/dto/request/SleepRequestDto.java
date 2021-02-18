package com.room.manage.api.participation.model.dto.request;

import com.room.manage.api.participation.model.entity.SleepReason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@ApiModel("부재 요청 Dto")
@AllArgsConstructor
public class SleepRequestDto {
    @ApiModelProperty("부재 종료시간 (yyyy-MM-dd-HH:mm) mm은 10분단위로 (0,10,20,30,40,50)")
    String date;
    @ApiModelProperty("부재 이유")
    SleepReason reason;
}
