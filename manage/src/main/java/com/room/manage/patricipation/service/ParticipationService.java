package com.room.manage.patricipation.service;

import com.room.manage.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.patricipation.model.dto.response.SleepInfoResponseDto;
import com.room.manage.patricipation.model.dto.request.SleepRequestDto;

import java.util.Date;

public interface ParticipationService {
    ParticipationResponseDto joinRoom(ParticipationRequestDto participationRequestDto);
    void exitRoom(Long userId);
    SleepInfoResponseDto toSleepStatus(Long id,SleepRequestDto sleepRequestDto);
    Date extendTime(Long userId,ExtendTimeRequestDto extendTimeRequestDto);
}
