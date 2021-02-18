package com.room.manage.api.room.model.dto;

import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.room.model.entity.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@ApiModel("Room 간단 정보(리스트 용)")
@AllArgsConstructor
public class SimpleRoomInfoDto {

    @ApiModelProperty("층")
    String floor;

    @ApiModelProperty("호")
    String field;

    @ApiModelProperty("최대 인원")
    int maxNum;

    @ApiModelProperty("현재 인원")
    int nowNum;

    @ApiModelProperty("부재 인원")
    int sleepNum;

    @ApiModelProperty("룸 상태")
    Status roomStatus;

    @ApiModelProperty("참여자들 정보")
    String participantInfo;

    public SimpleRoomInfoDto(Room room)
    {
        floor = room.getFloor();
        field = room.getField();
        maxNum = room.getMaxNum();
        nowNum = room.getNowNum();
        sleepNum = room.getSleepNum();
        roomStatus = room.getStatus();
        participantInfo = room.getNowNum() == 0?"none":room.getParticipates().get(0).getParticipant().getName()+"외"+(room.getNowNum()-1)+"명";
    }
}