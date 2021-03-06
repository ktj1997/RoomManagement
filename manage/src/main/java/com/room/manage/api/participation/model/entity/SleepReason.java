package com.room.manage.api.participation.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public enum SleepReason {
    @ApiModelProperty("화장실")
    TOILET,
    @ApiModelProperty("식사")
    MEAL,
    @ApiModelProperty("수업")
    CLASS,
    @ApiModelProperty("그 외")
    OTHER
}
