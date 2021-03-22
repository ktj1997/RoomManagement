package com.room.manage.api.alarm.event;

import com.room.manage.api.alarm.service.AlarmService;
import com.room.manage.api.participation.event.ExitSuccessEvent;
import com.room.manage.api.participation.event.joinSuccessEvent;
import com.room.manage.api.participation.exception.AlarmExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventHandler {
    private final AlarmService alarmService;

    @Order(2)
    @EventListener
    public void joinAlarmEventHandler(joinSuccessEvent event) {
        alarmService.send(event.getUser(), event.getRoom(), event.getType());
    }

    @Order(2)
    @EventListener
    public void exitAlarmEventHandler(ExitSuccessEvent event) {
        try {
            alarmService.send(event.getUser(), event.getRoom(), event.getType());
        } catch (AlarmExecutionException e) {
            e.printStackTrace();
            throw e;
        } finally {
            event.getUser().setFcmToken(null);
        }
    }
}
