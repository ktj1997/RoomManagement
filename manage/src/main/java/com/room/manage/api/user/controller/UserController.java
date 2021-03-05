package com.room.manage.api.user.controller;

import com.room.manage.api.common.Response;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.user.service.UserService;
import com.room.manage.core.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("내가 참여중인 스터디룸")
    @GetMapping("/participation")
    public Response getParticipation() {
        Long userId = SecurityUtil.getUserIdFromToken();
        return new Response(HttpStatus.OK, userService.findMyParticipation(userId));
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Firebase 토큰 갱신")
    @PutMapping("/token")
    public Response renewalFcmToken(@RequestParam String token) {
        Long userId = SecurityUtil.getUserIdFromToken();
        return new Response(HttpStatus.OK, userService.renewalFcmToken(userId, token));
    }
}
