package com.room.manage.api.room.event;

import com.room.manage.api.participation.event.ExitSuccessEvent;
import com.room.manage.api.participation.event.ParticipationSuccessEvent;
import com.room.manage.api.participation.event.SleepFinishedEvent;
import com.room.manage.api.participation.event.SleepSuccessEvent;
import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.api.room.model.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class RoomEventHandler {

    @Order(1)
    @TransactionalEventListener
    public void participationSuccessEventHandler(ParticipationSuccessEvent event) {
        Room room = event.getRoom();
        room.join(event.getParticipation());
    }
    @TransactionalEventListener
    public void exitSuccessEventHandler(ExitSuccessEvent event) {
        Room room = event.getRoom();
        room.exit(event.getParticipation());
    }

    @TransactionalEventListener
    public void sleepSuccessEventHandler(SleepSuccessEvent event) {
        Room room = event.getRoom();
        Participation participation = event.getParticipation();

        room.setSleepNum(room.getSleepNum() + 1);
        participation.toSleepStatus(event.getSleep());
    }

    @TransactionalEventListener
    public void sleepFinishedEventHandler(SleepFinishedEvent event) {
        Room room = event.getRoom();
        Participation participation = event.getParticipation();

        room.setSleepNum(room.getNowNum() - 1);
        participation.toActiveStatus();
    }
}
