package com.room.manage.api.patricipation.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
@Getter
public enum ParticipationStatus {
    @ApiModelProperty("부재 상태")
    SLEEP,
    @ApiModelProperty("참여 상태")
    ACTIVE
}
