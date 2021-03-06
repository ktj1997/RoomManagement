package com.room.manage.api.participation.service;

import com.room.manage.api.participation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.dto.response.ExitResponseDto;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.participation.model.dto.request.SleepRequestDto;
import com.room.manage.api.participation.model.entity.Sleep;

public interface ParticipationService {
    ParticipationResponseDto joinRoom(Long userId,ParticipationRequestDto participationRequestDto,String token);
    ParticipationResponseDto toSleepStatus(Long userId,SleepRequestDto sleepRequestDto);
    ParticipationResponseDto toActiveStatus(Sleep sleep);
    ParticipationResponseDto extendTime(Long userId,ExtendTimeRequestDto extendTimeRequestDto);
    ExitResponseDto exitRoom(Long userId);
}
