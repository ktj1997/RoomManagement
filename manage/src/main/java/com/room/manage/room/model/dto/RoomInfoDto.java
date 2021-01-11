package com.room.manage.room.model.dto;

import com.room.manage.room.model.entity.Room;
import com.room.manage.room.model.entity.RoomId;
import com.room.manage.room.model.entity.RoomType;
import com.room.manage.room.model.entity.Status;
import com.room.manage.user.model.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.mapping.Collection;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RoomInfoDto {
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

    private Status status;

    /**
     * 그룹방, 개인방
     */
    private RoomType roomType;

    /**
     * 참여자 목록
     */
    private List<UserInfoDto> participants;

    public RoomInfoDto(Room room){
        this.floor = room.getFloor();
        this.field = room.getField();
        this.status = room.getStatus();
        this.maxNum = room.getMaxNum();
        this.nowNum = room.getNowNum();
        this.roomType = room.getType();
        this.participants = room.getParticipates().stream().map(it -> new UserInfoDto(it.getParticipant())).collect(Collectors.toList());
    }
}
