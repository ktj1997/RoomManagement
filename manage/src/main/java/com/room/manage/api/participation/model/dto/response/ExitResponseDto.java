package com.room.manage.api.participation.model.dto.response;

import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.core.util.DateUtil;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@ApiModel("퇴실요청 결과 반환 Dto")
@AllArgsConstructor
public class ExitResponseDto {

    String participant;
    String floor;
    String field;
    String startTime;
    String finishTime;


    public ExitResponseDto(Participation participation) {
        this.participant = participation.getParticipant().getName();
        this.floor = participation.getRoom().getFloor();
        this.field = participation.getRoom().getField();
        this.startTime = DateUtil.formatToString(participation.getStartTime());
        this.finishTime = DateUtil.formatToString(new Date());

    }
}
