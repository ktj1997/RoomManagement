package com.room.manage.api.room.controller;

import com.room.manage.api.common.Response;
import com.room.manage.api.room.service.RoomService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    /**
     * Room 관련 정보 가져오기
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("스터디룸 현재상황")
    @GetMapping("/room")
    public Response getRoomInfo(@RequestParam String floor, @RequestParam String field) {
        return new Response(HttpStatus.OK, roomService.getRoomInfo(floor, field));
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("모든 스터디룸 상황")
    @GetMapping
    public Response getAllRoomInfo() {
        return new Response(HttpStatus.OK, roomService.getAllRoomsInfo());
    }
}
