package com.room.manage.factory;

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
