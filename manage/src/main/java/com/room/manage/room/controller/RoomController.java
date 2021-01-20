package com.room.manage.room.controller;

import com.room.manage.room.model.dto.RoomInfoDto;
import com.room.manage.room.service.RoomService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    /**
     * Room 관련 정보 가져오기
     */
    @ApiOperation("스터디룸 현재상황")
    @GetMapping("/info")
    public RoomInfoDto getRoomInfo(@RequestParam String floor, @RequestParam String field)
    {
       return roomService.getRoomInfo(floor,field);
    }
}
