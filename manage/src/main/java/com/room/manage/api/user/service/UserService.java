package com.room.manage.api.user.service;

import com.room.manage.api.patricipation.model.dto.response.ParticipationResponseDto;

public interface UserService {
    ParticipationResponseDto findMyParticipation(Long userId);
}
