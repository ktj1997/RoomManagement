package com.room.manage.api.patricipation.controller;

import com.room.manage.api.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.patricipation.model.dto.request.SleepRequestDto;
import com.room.manage.api.patricipation.service.ParticipationService;
import com.room.manage.core.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    /**
     *
     * @param participationRequestDto 참여에 필요한 정보
     * @param token fcmToken
     * @return
     */
    @ApiOperation("입실")
    @PostMapping
    public ParticipationResponseDto joinRoom(@RequestBody ParticipationRequestDto participationRequestDto,@RequestParam(required = false) String token)
    {
        return participationService.joinRoom(participationRequestDto,token);
    }

    @ApiOperation("퇴실")
    @DeleteMapping
    public void exitRoom()
    {
        participationService.exitRoom(SecurityUtil.getUserIdFromToken());
    }

    /**
     * 이미 참여하고 있을 때, 시간 연장
     * @param extendTimeRequestDto
     */
    @ApiOperation("시간 연장")
    @PutMapping("/extend")
    public ParticipationResponseDto extendParticipation(@RequestBody ExtendTimeRequestDto extendTimeRequestDto)
    {
        return participationService.extendTime(extendTimeRequestDto);
    }

    /**
     * 부재 상태로 변경
     * @param sleepRequestDto
     * @return SleepInfoDto
     */
    @ApiOperation("부재 신청")
    @PutMapping("/sleep")
    public ParticipationResponseDto toSleepStatus(@RequestBody SleepRequestDto sleepRequestDto)
    {
        return participationService.toSleepStatus(sleepRequestDto);
    }
}
