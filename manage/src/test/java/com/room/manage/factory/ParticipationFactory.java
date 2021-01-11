package com.room.manage.factory;

import com.room.manage.patricipation.model.dto.ParticipationRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ParticipationFactory {

    private Long userId = 1L;
    private String floor = "3";
    private String field = "B";
    private int hour = 3;
    private int minute = 0;

    public ParticipationRequestDto getParticipationRequestDto()
    {
        return new ParticipationRequestDto(userId,floor,field,hour,minute);
    }

}
