package com.room.manage.patricipation.service;

import com.room.manage.patricipation.model.dto.ParticipationRequestDto;
import com.room.manage.patricipation.model.entity.SleepReason;
import com.room.manage.room.model.entity.RoomType;

public interface ParticipationService {
    void joinRoom(ParticipationRequestDto participationRequestDto);
    void exitRoom(Long userId);
    void toSleepStatus(Long userId, SleepReason sleepReason);
}
