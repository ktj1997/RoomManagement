package com.room.manage.patricipation.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@ApiModel
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Sleep {
    /**
     * 부재 시작시간
     */
    @ApiModelProperty("부재 시작 시간 (yyyy-MM-dd-HH:mm) mm은 10분단위로 (0,10,20,30,40,50)")
    Date sleepStartTime;

    /**
     * 부재 종료시간
     */
    @ApiModelProperty("부재 종료시간 (yyyy-MM-dd-HH:mm) mm은 10분단위로 (0,10,20,30,40,50)")
    Date sleepFinishTime;
    /**
     * 부재 이유
     */
    @ApiModelProperty("부재 이유")
    @Enumerated(EnumType.STRING)
    SleepReason reason;
}
