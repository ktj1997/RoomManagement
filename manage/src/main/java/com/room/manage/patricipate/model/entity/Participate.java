package com.room.manage.patricipate.model.entity;

import com.room.manage.sleep.exception.SleepRequestDenyException;
import com.room.manage.room.model.Room;
import com.room.manage.room.model.Status;
import com.room.manage.sleep.model.Sleep;
import com.room.manage.sleep.model.SleepReason;
import com.room.manage.user.model.User;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
public class Participate {
    @Id @GeneratedValue
    private Long id;
    /**
     *  참여자, 개인방이면 개인들의 목록,
     *  그룹방이면 대표자 한명
     *  1:1~N
     */
    @OneToMany()
    private List<User> participants = new ArrayList<>();
    /**
     * 시작 시간
     */
    private Date startTime = new Date();

    /**
     *  종료 시간
     */
    private Date finishTime;

    /**
     * 남은 부재 가능 횟수
     */
    private int remainSleepNum = 3;

    public void addParticipant(User user){
        participants.add(user);
    }

    @Builder
    public Participate(User user,Date finishTime)
    {
        addParticipant(user);
        finishTime = finishTime;
    }



}
