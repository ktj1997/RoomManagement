package com.room.manage.api.patricipation.service.function;

import com.room.manage.api.patricipation.model.entity.AlarmType;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.user.model.entity.User;

public interface SendAlarm {
    void send(User user, Room room, AlarmType alarmType);
}
