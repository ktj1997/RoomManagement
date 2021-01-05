package com.room.manage.room.model.dto;

import com.room.manage.room.model.entity.Room;
import com.room.manage.room.model.entity.RoomType;
import com.room.manage.user.model.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RoomInfoDto {

    /**
     * Room 식별자
     */
    private Long id;

    /**
     * 층
     */
    private String floor;
    /**
     * 호수
     */
    private String field;

    /**
     * 최대 수용인원
     */
    private int maxNum;
    /**
     * 현재 인원
     */
    private int nowNum;

    /**
     * 그룹방, 개인방
     */
    private RoomType roomType;

    /**
     * 참여자 목록
     */
    private List<UserInfoDto> participants;

    public RoomInfoDto(Room room, List<UserInfoDto> participants){
        this.id = room.getId();
        this.floor = room.getFloor();
        this.field = room.getField();
        this.maxNum = room.getMaxNum();
        this.nowNum = room.getNowNum();
        this.roomType = room.getType();
        this.participants = participants;
    }
}
