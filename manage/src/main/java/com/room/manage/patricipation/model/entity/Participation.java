package com.room.manage.patricipation.model.entity;

import com.room.manage.room.model.entity.Room;
import com.room.manage.user.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Participation {
    @Id @GeneratedValue
    private Long id;

    /**
     * 시작 시간
     */
    @Column(nullable = false)
    private Date startTime = new Date();

    /**
     *  종료 시간
     */
    @Column(nullable = false)
    private Date finishTime;

    /**
     * 현재 상태(부재,활동)
     */
    @Enumerated(EnumType.STRING)
    ParticipationType participationType;

    /**
     * 남은 부재 가능 횟수
     */
    private int remainSleepNum = 3;

    /**
     *  참여자, 개인방이면 개인들의 목록,
     *  그룹방이면 대표자 한명
     *  1:1~N
     */
    @OneToMany(mappedBy = "participation")
    private List<User> participants = new ArrayList<>();

    /**
     * 참여하고 있는 방
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "floor",referencedColumnName = "floor"),
            @JoinColumn(name= "field",referencedColumnName = "field")
    })
    Room room;

    /**
     * 부재 정보
     */
    @Embedded
    Sleep sleep = null;

    public void addParticipant(User user){
        participants.add(user);
    }

    public void toSleepStatus(Sleep sleep){
        this.sleep = sleep;
    }

    @Builder
    public Participation(User user, Date finishTime,Room room)
    {
        addParticipant(user);
        this.finishTime = finishTime;
        this.room = room;
    }



}
