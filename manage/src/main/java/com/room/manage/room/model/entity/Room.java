package com.room.manage.room.model.entity;

import com.room.manage.patricipation.model.entity.Participation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public Room(int maxNum,String floor,String field,RoomType type)
    {
        this.maxNum = maxNum;
        this.floor = floor;
        this.type = type;
        this.field = field;
    }

}
