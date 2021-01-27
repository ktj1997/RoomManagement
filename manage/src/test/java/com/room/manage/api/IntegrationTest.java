package com.room.manage.api;

import com.room.manage.api.auth.service.AuthService;
import com.room.manage.api.patricipation.repository.ParticipationRepository;
import com.room.manage.api.patricipation.service.ParticipationService;
import com.room.manage.api.room.repository.RoomRepository;
import com.room.manage.api.user.service.UserService;
import com.room.manage.factory.CommonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    protected CommonFactory commonFactory;

    @Autowired
    protected ParticipationService participationService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected RoomRepository roomRepository;

    @Autowired
    protected ParticipationRepository participationRepository;
}
