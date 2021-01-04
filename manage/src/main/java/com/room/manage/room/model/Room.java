package com.room.manage.room.model;

import com.room.manage.room.exception.AlreadyMaximumParticipantException;
import com.room.manage.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Room {

    @Id @GeneratedValue
    private Long id;

    private int maxNum;

    private int nowNum=0;

    @Enumerated(EnumType.STRING)
    private Status status = Status.EMPTY;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private String floor;

    private String field;

    @Builder
    public Room(int maxNum,String floor,String field,RoomType type)
    {
        this.maxNum = maxNum;
        this.floor = floor;
        this.type = type;
        this.field = field;
    }

}
