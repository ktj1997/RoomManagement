package com.room.manage.sleep.model;

import com.room.manage.patricipate.model.entity.Participate;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
public class Sleep {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Participate participate;

    Date sleepTime;

    @Enumerated(EnumType.STRING)
    SleepReason reason;

    @Builder
    public Sleep(Participate participate, Date sleepTime, SleepReason reason){
        this.participate = participate;
        this.sleepTime = sleepTime;
        this.reason = reason;
    }
}
