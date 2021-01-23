package com.room.manage.api.patricipation.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@ApiModel
@Getter
@NoArgsConstructor
public class Sleep {

    @Id @GeneratedValue
    private Long id;
    /**
     * 부재 시작시간
     */
    @ApiModelProperty("부재 시작 시간 (yyyy-MM-dd-HH:mm) mm은 10분단위로 (0,10,20,30,40,50)")
    private Date sleepStartTime;

    /**
     * 부재 종료시간
     */
    @ApiModelProperty("부재 종료시간 (yyyy-MM-dd-HH:mm) mm은 10분단위로 (0,10,20,30,40,50)")
    private Date sleepFinishTime;
    /**
     * 부재 이유
     */
    @ApiModelProperty("부재 이유")
    @Enumerated(EnumType.STRING)
    private SleepReason reason;

    @Setter
    @OneToOne
    private Participation participation;

    public Sleep(Date sleepStartTime,Date sleepFinishTime, SleepReason reason, Participation participation){
        this.sleepStartTime = sleepStartTime;
        this.sleepFinishTime = sleepFinishTime;
        this.reason = reason;
        this.participation = participation;
    }
}
