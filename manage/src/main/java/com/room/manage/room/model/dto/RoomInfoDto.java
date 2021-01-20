package com.room.manage.room.model.dto;

import com.room.manage.room.model.entity.Room;
import com.room.manage.room.model.entity.Status;
import com.room.manage.user.model.dto.UserInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@ApiModel
@Getter
@AllArgsConstructor
public class RoomInfoDto {
    /**
     * 층
     */
    @ApiModelProperty("층")
    private String floor;
    /**
     * 호수
     */
    @ApiModelProperty("호수")
    private String field;

    /**
     * 최대 수용인원
     */
    @ApiModelProperty("최대 수용인원")
    private int maxNum;
    /**
     * 현재 인원
     */
    @ApiModelProperty("현재 인원")
    private int nowNum;

    /**
     * 현재 상태
     */
    @ApiModelProperty("방의 현재 상태")
    private Status status;

    /**
     * 참여자 목록
     */
    @ApiModelProperty("참여자 목록")
    private List<UserInfoDto> participants;

    public RoomInfoDto(Room room){
        this.floor = room.getFloor();
        this.field = room.getField();
        this.status = room.getStatus();
        this.maxNum = room.getMaxNum();
        this.nowNum = room.getNowNum();
        this.participants = room.getParticipates().stream().map(it -> new UserInfoDto(it.getParticipant())).collect(Collectors.toList());
    }
}
