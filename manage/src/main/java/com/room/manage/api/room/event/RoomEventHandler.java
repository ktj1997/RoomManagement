package com.room.manage.api.room.event;

import com.room.manage.api.participation.event.ExitSuccessEvent;
import com.room.manage.api.participation.event.joinSuccessEvent;
import com.room.manage.api.participation.event.SleepFinishedEvent;
import com.room.manage.api.participation.event.SleepSuccessEvent;
import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.api.room.model.entity.Room;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class RoomEventHandler {

    @Order(1)
    @EventListener
    public void joinSuccessEventHandler(joinSuccessEvent event) {
        Room room = event.getRoom();
        room.join(event.getParticipation());
    }

    @Order(1)
    @EventListener
    public void exitSuccessEventHandler(ExitSuccessEvent event) {
        Room room = event.getRoom();
        room.exit(event.getParticipation());
    }

    @EventListener
    public void sleepSuccessEventHandler(SleepSuccessEvent event) {
        Room room = event.getRoom();
        Participation participation = event.getParticipation();

        room.setSleepNum(room.getSleepNum() + 1);
        participation.toSleepStatus(event.getSleep());
    }

    @EventListener
    public void sleepFinishedEventHandler(SleepFinishedEvent event) {
        Room room = event.getRoom();
        Participation participation = event.getParticipation();

        room.setSleepNum(room.getNowNum() - 1);
        participation.toActiveStatus();
    }
}
