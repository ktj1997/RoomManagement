package com.room.manage.api.patricipation.model.dto.response;

import com.room.manage.api.patricipation.model.entity.Participation;
import com.room.manage.api.patricipation.model.entity.ParticipationStatus;
import com.room.manage.api.patricipation.model.entity.Sleep;
import com.room.manage.core.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ApiModel("참가요청 결과 반환 Dto")
@AllArgsConstructor
public class ParticipationResponseDto {
    /**
     * 유저 식별자
     */
    @ApiModelProperty("유저 식별자")
    Long userId;
    /**
     * 유저 이름
     */
    @ApiModelProperty("유저 이름")
    String name;
    /**
     * 참여하는 Room의 층
     */
    @ApiModelProperty("층 (3,4,5)")
    private String floor;
    /**
     * 참여하는 Room의 호
     */
    @ApiModelProperty("호수 (A,B,C)")
    private String field;

    /**
     * 시작 시간
     */
    @ApiModelProperty("시작시간 (yyyy-MM-dd-HH:mm) mm은 10분단위로 (0,10,20,30,40,50)")
    private String startTime;
    /**
     * 종료 시간
     */
    @ApiModelProperty("종료시간 (yyyy-MM-dd-HH:mm) mm은 10분단위로 (0,10,20,30,40,50)")
    private String finishTime;
    /**
     * 남은 부재가능 횟수
     */
    @ApiModelProperty("남은 부재 가능 횟수")
    private int remainSleepNum;

    /**
     * 현재 상태(부재,활동)
     */
    @ApiModelProperty("현재 참여 상태")
    private ParticipationStatus participationStatus;

    /**
     * 부재 정보
     */
    @ApiModelProperty("부재 정보")
    private SleepDto sleep;

    public ParticipationResponseDto(Participation participation){
        this.userId = participation.getParticipant().getId();
        this.name = participation.getParticipant().getName();
        this.floor = participation.getRoom().getFloor();
        this.field = participation.getRoom().getField();
        this.startTime = DateUtil.formatToString(participation.getStartTime());
        this.finishTime = DateUtil.formatToString(participation.getFinishTime());
        this.remainSleepNum = participation.getRemainSleepNum();
        this.participationStatus = participation.getParticipationStatus();
        this.sleep = participation.getSleep() == null ? null:new SleepDto(participation.getSleep());
    }
    public ParticipationResponseDto(Participation participation, Sleep sleep){
        this.userId = participation.getParticipant().getId();
        this.name = participation.getParticipant().getName();
        this.floor = participation.getRoom().getFloor();
        this.field = participation.getRoom().getField();
        this.startTime = DateUtil.formatToString(participation.getStartTime());
        this.finishTime = DateUtil.formatToString(participation.getFinishTime());
        this.remainSleepNum = participation.getRemainSleepNum();
        this.participationStatus = participation.getParticipationStatus();
        this.sleep = new SleepDto(sleep);
    }

}
