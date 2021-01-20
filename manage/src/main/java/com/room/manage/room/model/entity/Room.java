package com.room.manage.room.model.entity;

import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.user.model.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
@NoArgsConstructor
@IdClass(RoomId.class)
public class Room {

    @ApiModelProperty("층")
    @Id
    @Column(length = 2)
    private String floor;

    @ApiModelProperty("호수")
    @Id
    @Column(length = 2)
    private String field;

    @Column(nullable = false,length = 2)
    private int maxNum;

    @ApiModelProperty("휴재 중인 인원수")
    @Setter
    @Column(nullable = false,length = 2)
    private int sleepNum=0;

    @ApiModelProperty("현재 참여하고있는 인원 수")
    @Setter
    @Column(nullable = false,length = 2)
    private int nowNum=0;

    @ApiModelProperty("현재 상태")
    @Setter
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Status status = Status.EMPTY;

    @OneToMany(mappedBy = "room")
    List<Participation> participates = new ArrayList<>();

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

    public void init()
    {
        this.status = Status.EMPTY;
        this.nowNum = 0;
        this.sleepNum = 0;
    }
}
