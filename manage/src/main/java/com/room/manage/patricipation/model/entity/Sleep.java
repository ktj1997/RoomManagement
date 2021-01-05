package com.room.manage.patricipation.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Sleep {
    /**
     * 부재 기간
     */
    Date sleepTime;

    /**
     * 부재 이유
     */
    @Enumerated(EnumType.STRING)
    SleepReason reason;
}
