package com.room.manage.room.model.entity;

import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.user.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.event.service.internal.EventListenerServiceInitiator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@IdClass(RoomId.class)
public class Room {

    @Id
    @Column(length = 2)
    private String floor;

    @Id
    @Column(length = 2)
    private String field;

    @Column(nullable = false,length = 2)
    private int maxNum;

    @Setter
    @Column(nullable = false,length = 2)
    private int sleepNum=0;

    @Setter
    @Column(nullable = false,length = 2)
    private int nowNum=0;

    @Setter
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Status status = Status.EMPTY;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @OneToMany(mappedBy = "room")
    List<Participation> participates = new ArrayList<>();

    /**
     * 대표자 (개인 방은 null, 그룹 방은 필수)
     */
    @Setter
    @OneToOne
    User delegate = null;

    /**
     * 퇴실
     */
    public void exit()
    {
        if(nowNum <= 0)
            throw new RuntimeException("현재 참여인원수가 음수가 될 수 없습니다.");
        else{
            nowNum -- ;
            if(nowNum == 0)
                status = Status.EMPTY;
        }
    }

    /**
     * 입실
     */
    public void join()
    {
        if(nowNum==0)
            status = Status.ACTIVATE;
        nowNum++;
    }
    public boolean canJoin()
    {
        return this.nowNum+1 <= this.maxNum;
    }
}
