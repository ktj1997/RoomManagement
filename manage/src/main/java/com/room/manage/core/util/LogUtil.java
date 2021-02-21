package com.room.manage.core.util;

import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;

import java.util.Date;
import java.util.Map;

public class LogUtil {

    public static String joinLogStringFormat(String userName, ParticipationRequestDto participationRequestDto){
        return String.format("%s::: %s 님이 %s층 %s호 입장!",DateUtil.formatToString(new Date()),userName
                ,participationRequestDto.getFloor(),participationRequestDto.getField());
    }

    public static String exitLogStringFormat(String userName){
        return String.format("%s::: %s 님이 퇴장!",DateUtil.formatToString(new Date()),userName);
    }

}
