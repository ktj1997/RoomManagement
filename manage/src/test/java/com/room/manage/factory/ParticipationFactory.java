package com.room.manage.factory;

import com.room.manage.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.patricipation.model.dto.request.SleepRequestDto;
import com.room.manage.patricipation.model.entity.SleepReason;
import org.springframework.stereotype.Component;

@Component
public class ParticipationFactory {

    /**
     * Participation
     */
    private Long userId = 1L;
    private String floor = "3";
    private String field = "B";
    private int hour = 3;
    private int minute = 0;

    /**
     * Sleep
     */
    private String sleepFinishTime = "17:30";
    private SleepReason reason = SleepReason.CLASS;

    /**
     * Extend
     */
    private int extendHour = 1;
    private int extendMinute = 30;

    public ParticipationRequestDto getParticipationRequestDto()
    {
        return new ParticipationRequestDto(userId,floor,field,hour,minute);
    }

    public ExtendTimeRequestDto getExtendTimeRequestDto()
    {
        return new ExtendTimeRequestDto(extendHour,extendMinute);
    }

    public SleepRequestDto getSleepRequestDto()
    {
        return new SleepRequestDto(sleepFinishTime,reason);
    }

}
