package com.room.manage.api.user.service;

import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;

public interface UserService {
    ParticipationResponseDto findMyParticipation(Long userId);

    String renewalFcmToken(Long userId, String token);
}
