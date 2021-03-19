package com.room.manage.api.participation.event;

import com.room.manage.api.participation.model.entity.AlarmType;
import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ParticipationSuccessEvent {
    private Room room;
    private User user;
    private AlarmType type;
    private Participation participation;
}
