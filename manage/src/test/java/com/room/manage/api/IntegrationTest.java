package com.room.manage.api;

import com.room.manage.api.auth.service.AuthService;
import com.room.manage.api.participation.repository.ParticipationRepository;
import com.room.manage.api.participation.service.ParticipationService;
import com.room.manage.api.room.repository.RoomRepository;
import com.room.manage.api.room.service.RoomService;
import com.room.manage.api.user.repository.UserRepository;
import com.room.manage.api.user.service.UserService;
import com.room.manage.factory.CommonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class IntegrationTest {

    @Autowired
    protected CommonFactory commonFactory;

    @Autowired
    protected ParticipationService participationService;

    @Autowired
    protected RoomService roomService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected RoomRepository roomRepository;

    @Autowired
    protected ParticipationRepository participationRepository;

    @Autowired
    protected UserRepository userRepository;
}
