package com.room.manage.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static Calendar cal = Calendar.getInstance();
    private static SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
    private static SimpleDateFormat hourAndMinuteFormat = new SimpleDateFormat("HH:mm");

    private static final String StartTime = "08:00";
    private static final String FinishTime ="23:00";


    public static Date formatToDate(String date) {
        try {
            return fullDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String formatToString(Date date)
    {
        return fullDateFormat.format(date);
    }

    /**
     * 이용시간인 8시부터 23시까지에 만족하는지, 지금보다 이후의 시간인지 확인
     */
    public static boolean checkValidDate(String date){
        String[] token = date.split("-");
        int length = token.length;
        Date fullDateTime=null,targetTime = null,startTime=null,finishTime=null,now=new Date();
        try {
            fullDateTime = fullDateFormat.parse(date);
            targetTime = hourAndMinuteFormat.parse(token[length-1]);
            startTime = hourAndMinuteFormat.parse(StartTime);
            finishTime = hourAndMinuteFormat.parse(FinishTime);
            now = hourAndMinuteFormat.parse(hourAndMinuteFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
            if(targetTime.before(startTime)
                    || targetTime.after(finishTime)
                    || targetTime.before(now)
                    || fullDateTime.before(new Date()))
                return false;
        return true;
    }
}
