package com.room.manage.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static Calendar cal = Calendar.getInstance();

    public static Date plusTime(int hour,int minute)
    {
        cal.setTime(new Date());
        cal.add(Calendar.HOUR,hour);
        cal.add(Calendar.MINUTE,minute);

        return cal.getTime();
    }
    public static Date plusTime(Date date,int hour,int minute)
    {
        cal.setTime(date);
        cal.add(Calendar.HOUR,hour);
        cal.add(Calendar.MINUTE,minute);

        return cal.getTime();
    }
}
