package com.room.manage.factory;

import com.room.manage.api.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.patricipation.model.dto.request.SleepRequestDto;
import com.room.manage.api.patricipation.model.entity.SleepReason;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ParticipationFactory {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Participation
     */
    private Long userId = 1L;
    private String floor = "3";
    private String field = "B";
    private String date = simpleDateFormat.format(new Date());
    private String endTime = "22:30";

    /**
     * Sleep
     */
    private String sleepFinishTime = "17:30";
    private SleepReason reason = SleepReason.CLASS;

    /**
     * Extend
     */
    private String extendTime = "23:00";

    public ParticipationRequestDto getParticipationRequestDto()
    {
        return new ParticipationRequestDto(floor,field,date+"-"+endTime);
    }

    public ExtendTimeRequestDto getExtendTimeRequestDto()
    {
        return new ExtendTimeRequestDto(date+"-"+extendTime);
    }

    public SleepRequestDto getSleepRequestDto()
    {
        return new SleepRequestDto(date+"-"+sleepFinishTime,reason);
    }

}
