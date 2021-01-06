package com.room.manage.room.model.entity;

import com.room.manage.patricipation.model.entity.Participation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@IdClass(RoomId.class)
public class Room {

    @Id
    @Column(length = 1)
    private String floor;

    @Id
    @Column(length = 1)
    private String field;

    @Column(nullable = false,length = 2)
    private int maxNum;

    @Setter
    @Column(nullable = false,length = 2)
    private int nowNum;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Status status = Status.EMPTY;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @OneToMany(mappedBy = "room")
    List<Participation> participates = new ArrayList<>();

    /**
     * 개인 방 일 때 참여가능 여부
     * @return
     */
    public boolean canJoin()
    {
        if(nowNum<maxNum)
            return true;
        return false;
    }

    /**
     * 그룹 방 일 때 참여 가능 여부
     * @param participants 참가자 수
     * @return
     */
    public boolean canJoin(int participants)
    {
        if(nowNum+participants < maxNum)
            return true;
        return false;
    }

    /**
     * 개인 방 일때 참여
     */
    public void join()
    {
        this.nowNum++;
        this.status = Status.ACTIVATE;
    }

    /**
     * 그룹 방일 때 참여
     * @param participants
     */
    public void join(int participants)
    {
        this.nowNum = this.nowNum + participants;
        this.status = Status.ACTIVATE;
    }


}
