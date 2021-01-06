package com.room.manage.patricipation.model.dto;

import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.model.entity.ParticipationType;
import com.room.manage.patricipation.model.entity.Sleep;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ParticipationInfoDto {
    /**
     * 참여하는 Room의 식별자
     */
    private Long roomId;
    /**
     * 참여하는 Room의 층
     */
    private String floor;
    /**
     * 참여하는 Room의 호
     */
    private String field;

    /**
     * 시작 시간
     */
    private Date startTime;
    /**
     * 종료 시간
     */
    private Date finishTime;
    /**
     * 남은 부재가능 횟수
     */
    private int remainSleepNum;

    /**
     * 현재 상태(부재,활동)
     */
    private ParticipationType participationType;

    /**
     * 부재 정보
     */
    private Sleep sleep;

    public ParticipationInfoDto(Participation participation){
        this.floor = participation.getRoom().getFloor();
        this.field = participation.getRoom().getField();
        this.startTime = participation.getStartTime();
        this.finishTime = participation.getFinishTime();
        this.remainSleepNum = participation.getRemainSleepNum();
        this.participationType = participation.getParticipationType();
        this.sleep = participation.getSleep();
    }

}
