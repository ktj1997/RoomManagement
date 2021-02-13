package com.room.manage.api.room.controller;

import com.room.manage.api.room.model.dto.DetailRoomInfoDto;
import com.room.manage.api.room.model.dto.SimpleRoomInfoDto;
import com.room.manage.api.room.service.RoomService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    /**
     * Room 관련 정보 가져오기
     */
    @ApiOperation("스터디룸 현재상황")
    @GetMapping("/room")
    public DetailRoomInfoDto getRoomInfo(@RequestParam String floor, @RequestParam String field)
    {
       return roomService.getRoomInfo(floor,field);
    }

    @ApiOperation("모든 스터디룸 상황")
    public List<SimpleRoomInfoDto> getAllRoomInfo()
    {
        return roomService.getAllRoomsInfo();
    }
}
