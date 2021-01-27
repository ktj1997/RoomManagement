package com.room.manage.core.util;

public class AlarmUtil {
    public static String joinMessage(String name){
        return String.format("%s 님이 입실 하였습니다.",name);
    }
    public static String exitMessage(String name){
        return String.format("%s님이 퇴실 하였습니다.",name);
    }
}
