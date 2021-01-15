package com.room.manage.util;

import org.hibernate.boot.model.relational.SimpleAuxiliaryDatabaseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static Calendar cal = Calendar.getInstance();
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    public static Date formatToDate(String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String formatToString(Date date)
    {
        return format.format(date);
    }
}
