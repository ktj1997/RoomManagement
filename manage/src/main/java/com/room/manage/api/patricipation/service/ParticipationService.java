package com.room.manage.api.patricipation.service;

import com.room.manage.api.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.patricipation.model.dto.request.SleepRequestDto;

import java.util.Date;

public interface ParticipationService {
    ParticipationResponseDto joinRoom(ParticipationRequestDto participationRequestDto);
    void exitRoom(Long userId);
    ParticipationResponseDto toSleepStatus(SleepRequestDto sleepRequestDto);
    Date extendTime(ExtendTimeRequestDto extendTimeRequestDto);
}
