package com.room.manage.factory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonFactory {
    @Autowired
    public UserFactory userFactory;

    @Autowired
    public ParticipationFactory participationFactory;

    @Autowired
    public RoomFactory roomFactory;

}
