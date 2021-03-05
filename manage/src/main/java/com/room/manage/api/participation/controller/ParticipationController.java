package com.room.manage.api.participation.controller;

import com.room.manage.api.common.Response;
import com.room.manage.api.participation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.dto.request.SleepRequestDto;
import com.room.manage.api.participation.service.ParticipationService;
import com.room.manage.core.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    /**
     * @param participationRequestDto 참여에 필요한 정보
     * @param token                   fcmToken
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("입실")
    @PostMapping
    public Response joinRoom(@RequestBody ParticipationRequestDto participationRequestDto, @RequestParam(required = false) String token) {
        return new Response(HttpStatus.CREATED, participationService.joinRoom(participationRequestDto, token));
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("퇴실")
    @DeleteMapping
    public Response exitRoom() {
        return new Response(HttpStatus.OK, participationService.exitRoom(SecurityUtil.getUserIdFromToken()));
    }

    /**
     * 이미 참여하고 있을 때, 시간 연장
     *
     * @param extendTimeRequestDto
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("시간 연장")
    @PutMapping("/extend")
    public Response extendParticipation(@RequestBody ExtendTimeRequestDto extendTimeRequestDto) {
        return new Response(HttpStatus.OK, participationService.extendTime(extendTimeRequestDto));
    }

    /**
     * 부재 상태로 변경
     *
     * @param sleepRequestDto
     * @return SleepInfoDto
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("부재 신청")
    @PutMapping("/sleep")
    public Response toSleepStatus(@RequestBody SleepRequestDto sleepRequestDto) {
        return new Response(HttpStatus.OK, participationService.toSleepStatus(sleepRequestDto));
    }
}
