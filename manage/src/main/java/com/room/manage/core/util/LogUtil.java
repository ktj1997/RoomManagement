package com.room.manage.core.util;

import com.room.manage.api.participation.model.dto.response.ExitResponseDto;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;

import java.util.Date;

        public class LogUtil {

            public static String joinLogStringFormat(ParticipationResponseDto participationResponseDto) {
                return String.format("%s::: %s 님이 %s층 %s호 입장!", DateUtil.formatToString(new Date()), participationResponseDto.getParticipant()
                        , participationResponseDto.getFloor(), participationResponseDto.getField());
            }

            public static String exitLogStringFormat(ExitResponseDto exitResponseDto) {
        return String.format("%s::: %s 님이 %s층 %s호 퇴장!", DateUtil.formatToString(new Date()), exitResponseDto.getParticipant(), exitResponseDto.getFloor(), exitResponseDto.getField());
    }

}
