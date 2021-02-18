package com.room.manage.api.participation.service.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class ParticipationCacheFunction {

    @CacheEvict(cacheNames = "com.room.manage.api.participation.model.entity.Participation",key = "#participationId")
    public void removeParticipationCache(Long participationId){}
}
