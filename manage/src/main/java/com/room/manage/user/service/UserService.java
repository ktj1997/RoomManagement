package com.room.manage.user.service;

import com.room.manage.patricipation.model.dto.response.ParticipationResponseDto;

public interface UserService {
    ParticipationResponseDto findMyParticipation(Long userId);
}
