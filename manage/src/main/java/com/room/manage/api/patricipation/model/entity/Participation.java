package com.room.manage.api.patricipation.model.entity;

import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.user.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
public class Participation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 시작 시간
     */
    @Column(nullable = false)
    private Date startTime = new Date();

    /**
     *  종료 시간
     */
    @Setter
    @Column(nullable = false)
    private Date finishTime;

    /**
     * 현재 상태(부재,활동)
     */
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    ParticipationStatus participationStatus;

    /**
     * 남은 부재 가능 횟수
     */
    @Setter
    private int remainSleepNum = 3;

    @OneToOne(optional = false)
    User participant;

    /**
     * 참여하고 있는 방
     */
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "floor",referencedColumnName = "floor"),
            @JoinColumn(name= "field",referencedColumnName = "field")
    })
    Room room;

    /**
     * 부재 정보
     */
    @OneToOne(mappedBy ="participation",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    Sleep sleep = null;

    public void toSleepStatus(Sleep sleep)
    {
        this.sleep = sleep;
        this.participationStatus = ParticipationStatus.SLEEP;
        this.remainSleepNum--;
    }

    public void toActiveStatus()
    {

        this.sleep = null;
        this.participationStatus = ParticipationStatus.ACTIVE;
    }

    @Builder
    public Participation(User user, Date finishTime, Room room, ParticipationStatus type,Sleep sleep)
    {
        this.participant = user;
        this.finishTime = finishTime;
        this.room = room;
        this.participationStatus = type;
        this.sleep = sleep;
    }
}
