package com.room.manage.user.service;

import com.room.manage.patricipation.model.dto.ParticipationInfoDto;

public interface UserService {
    ParticipationInfoDto findMyParticipation(Long userId);
}
