package com.room.manage.user;
import com.room.manage.auth.model.dto.SignUpRequestDto;
import com.room.manage.auth.service.AuthService;
import com.room.manage.factory.CommonFactory;
import com.room.manage.factory.UserFactory;
import com.room.manage.patricipation.exception.NoSuchParticipationException;
import com.room.manage.patricipation.service.ParticipationService;
import com.room.manage.user.model.entity.User;
import com.room.manage.user.repository.UserRepository;
import com.room.manage.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest{

    @Autowired
    UserService userService;

    @Autowired
    CommonFactory commonFactory;

    @Autowired
    AuthService authService;

    @Autowired
    ParticipationService participationService;

    private User user;

    @BeforeEach
    void setUser()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        user = authService.signUp(signUpRequestDto);
    }


    @Test
    @DisplayName("유저의 참여정보 리턴 테스트")
    public void findParticipationTest()
    {
        Assertions.assertAll(
                () -> Assertions.assertThrows(NoSuchParticipationException.class,() ->userService.findMyParticipation(user.getId()) ),
                () -> Assertions.assertDoesNotThrow(() -> participationService.joinRoom(commonFactory.participationFactory.getParticipationRequestDto())),
                () -> Assertions.assertDoesNotThrow(() -> userService.findMyParticipation(user.getId()))
        );
    }
}
