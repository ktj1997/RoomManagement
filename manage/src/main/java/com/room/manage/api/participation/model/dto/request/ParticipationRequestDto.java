package com.room.manage.api.participation.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel("참가 요청 Dto")
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto{
    @ApiModelProperty("층 (3,4,5)")
    String floor;
    @ApiModelProperty("호수 (A,B,C)")
    String field;
    /**
     * yyyy-MM-dd-HH:mm
     * 분은 10분단위 (ex: hour:0~24, minute:0 or 30)
     */
    @ApiModelProperty("종료시간 (yyyy-MM-dd-HH:mm) mm은 10분단위로 (0,10,20,30,40,50)")
    String finishTime;
}
