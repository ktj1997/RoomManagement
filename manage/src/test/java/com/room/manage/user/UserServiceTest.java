package com.room.manage.user;
import com.room.manage.user.model.entity.User;
import com.room.manage.user.repository.UserRepository;
import com.room.manage.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Mock
    UserRepository userRepository;


    @BeforeEach
    void setMockUser()
    {
        Mockito.when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new User(1L, "userId", "password", "dori", null)));
    }



    @Test
    @DisplayName("유저의 참여정보 리턴 테스트")
    void findParticipationTest()
    {
    }
}
