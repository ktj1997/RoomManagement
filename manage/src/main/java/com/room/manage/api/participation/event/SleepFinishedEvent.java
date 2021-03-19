package com.room.manage.api.participation.event;

import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.api.room.model.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SleepFinishedEvent {
    private Room room;
    private Participation participation;

}
