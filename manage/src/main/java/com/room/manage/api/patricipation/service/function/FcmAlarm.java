package com.room.manage.api.patricipation.service.function;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.room.manage.api.patricipation.exception.AlarmExecutionException;
import com.room.manage.api.patricipation.model.entity.AlarmType;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.core.util.AlarmUtil;
import org.springframework.stereotype.Component;

@Component
public class FcmAlarm implements SendAlarm{
    private final String JoinMsgTitle ="참여한 인원이 있습니다!";
    private final String ExitMsgTitle ="퇴실한 인원이 있습니다!";
    @Override
    public void send(User user, Room room, AlarmType alarmType) {
        String alarmMessage = alarmType.equals(AlarmType.JOIN)?JoinMsgTitle:ExitMsgTitle;
        FirebaseMessaging fcmSender = FirebaseMessaging.getInstance();

        room.getParticipates().stream().map(participation->participation.getParticipant())
                .forEach(participant -> {
                    try {
                        if(participant.getFcmToken() != null) {
                            Message message = Message.builder()
                                    .setToken(participant.getFcmToken())
                                    .setNotification(new Notification(alarmMessage, AlarmUtil.joinMessage(user.getName())))
                                    .build();
                            fcmSender.send(message);
                        }
                    }catch (FirebaseMessagingException e) {
                            throw new AlarmExecutionException();
                    }
                });
        }
}
