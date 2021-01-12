package com.room.manage.patricipation.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Sleep {
    /**
     * 부재 시작시간
     */
    Date sleepStartTime;

    /**
     * 부재 종료시간
     */
    Date sleepFinishTime;
    /**
     * 부재 이유
     */
    @Enumerated(EnumType.STRING)
    SleepReason reason;
}
