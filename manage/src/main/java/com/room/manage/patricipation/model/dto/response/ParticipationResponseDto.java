package com.room.manage.patricipation.model.dto.response;

import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.model.entity.ParticipationType;
import com.room.manage.patricipation.model.entity.Sleep;
import com.room.manage.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@AllArgsConstructor
@ToString
public class ParticipationResponseDto {
    /**
     * 유저 식별자
     */
    Long userId;
    /**
     * 유저 이름
     */
    String name;
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
    private String startTime;
    /**
     * 종료 시간
     */
    private String finishTime;
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

    public ParticipationResponseDto(Participation participation){
        this.userId = participation.getParticipant().getId();
        this.name = participation.getParticipant().getName();
        this.floor = participation.getRoom().getFloor();
        this.field = participation.getRoom().getField();
        this.startTime = DateUtil.formatToString(participation.getStartTime());
        this.finishTime = DateUtil.formatToString(participation.getFinishTime());
        this.remainSleepNum = participation.getRemainSleepNum();
        this.participationType = participation.getParticipationType();
        this.sleep = participation.getSleep();
    }

}
