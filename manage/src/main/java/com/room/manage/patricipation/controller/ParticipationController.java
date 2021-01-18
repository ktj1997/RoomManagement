package com.room.manage.patricipation.controller;

import com.room.manage.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.patricipation.model.dto.response.SleepInfoResponseDto;
import com.room.manage.patricipation.model.dto.request.SleepRequestDto;
import com.room.manage.patricipation.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    /**
     * 방 참여
     * @param participationRequestDto 참여에 필요한 정보
     */
    @PostMapping
    public ParticipationResponseDto makeParticipation(@RequestBody ParticipationRequestDto participationRequestDto)
    {
        return participationService.joinRoom(participationRequestDto);
    }

    /**
     * 이미 참여하고 있을 때, 시간 연장
     * @param extendTimeRequestDto
     */
    @PostMapping("/extend")
    public Date extendParticipation(@RequestParam Long userId,@RequestBody ExtendTimeRequestDto extendTimeRequestDto)
    {
        return participationService.extendTime(userId,extendTimeRequestDto);
    }

    /**
     * 부재 상태로 변경
     * @param sleepRequestDto
     * @return SleepInfoDto
     */
    @PostMapping("/sleep")
    public SleepInfoResponseDto toSleepStatus(@RequestParam Long userId,@RequestBody SleepRequestDto sleepRequestDto)
    {
        return participationService.toSleepStatus(userId,sleepRequestDto);
    }
}
