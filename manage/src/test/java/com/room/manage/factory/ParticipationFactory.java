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

    public String getDay() {
        return simpleDateFormat.format(new Date());
    }
}
