package com.room.manage.api.room.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public enum Status {
    @ApiModelProperty("참여 중")
    ACTIVATE,
    @ApiModelProperty("이용자 없음")
    EMPTY
}
