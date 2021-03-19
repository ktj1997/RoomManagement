package com.room.manage.api.alarm.event;

import com.room.manage.api.alarm.service.AlarmService;
import com.room.manage.api.participation.event.ExitSuccessEvent;
import com.room.manage.api.participation.event.ParticipationSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventHandler {
    private final AlarmService alarmService;

    @Async
    @Order(2)
    @EventListener
    public void joinAlarmEventHandler(ParticipationSuccessEvent event) {
        alarmService.send(event.getUser(), event.getRoom(), event.getType());
    }

    @Async
    @Order(2)
    @EventListener
    public void exitAlarmEventHandler(ExitSuccessEvent event) {
        alarmService.send(event.getUser(), event.getRoom(), event.getType());
        event.getUser().setFcmToken(null);
    }
}
