package com.room.manage.api.room.model.entity;

import com.room.manage.api.participation.model.entity.Participation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ApiModel
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@IdClass(RoomId.class)
public class Room{
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

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "room",fetch = FetchType.EAGER)
    List<Participation> participates = new ArrayList<>();

    /**
     * 퇴실
     */
    public void exit(Participation participation)
    {
        if(nowNum <= 0)
            throw new RuntimeException("현재 참여인원수가 음수가 될 수 없습니다.");
        else{
            nowNum -- ;
            this.participates.remove(participation);
            if(nowNum == 0)
                status = Status.EMPTY;
        }
    }

    /**
     * 입실
     */
    public void join(Participation participation)
    {
        if(nowNum==0)
            status = Status.ACTIVATE;
        nowNum++;
        this.participates.add(participation);
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
