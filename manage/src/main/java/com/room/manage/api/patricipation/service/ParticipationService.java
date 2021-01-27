package com.room.manage.api.patricipation.service;

import com.room.manage.api.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.patricipation.model.dto.request.SleepRequestDto;
import com.room.manage.api.patricipation.model.entity.Sleep;

import java.util.Date;

public interface ParticipationService {
    ParticipationResponseDto joinRoom(ParticipationRequestDto participationRequestDto);
    ParticipationResponseDto toSleepStatus(SleepRequestDto sleepRequestDto);
    ParticipationResponseDto toActiveStatus(Sleep sleep);
    ParticipationResponseDto extendTime(ExtendTimeRequestDto extendTimeRequestDto);
    void exitRoom(Long userId);
}
