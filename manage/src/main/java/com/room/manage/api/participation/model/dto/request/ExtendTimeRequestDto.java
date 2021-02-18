package com.room.manage.api.participation.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel("시간 연장 요청 Dto")
@NoArgsConstructor
@AllArgsConstructor
public class ExtendTimeRequestDto {
    /**
     * yyyy-MM-dd-HH:mm
     * 분은 10분단위 (ex: hour:0~24, minute:0 ~ 50)
     */
    @ApiModelProperty("종료시간 (yyyy-MM-dd-HH:mm) mm은 10분단위로 (0,10,20,30,40,50)")
    String finishTime;
}
