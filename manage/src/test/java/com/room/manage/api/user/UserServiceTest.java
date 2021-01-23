package com.room.manage.api.user;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.service.AuthService;
import com.room.manage.factory.CommonFactory;
import com.room.manage.api.patricipation.exception.NoParticipationException;
import com.room.manage.api.patricipation.service.ParticipationService;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
                () -> Assertions.assertThrows(NoParticipationException.class,() ->userService.findMyParticipation(user.getId()) ),
                () -> Assertions.assertDoesNotThrow(() -> participationService.joinRoom(commonFactory.participationFactory.getParticipationRequestDto())),
                () -> Assertions.assertDoesNotThrow(() -> userService.findMyParticipation(user.getId()))
        );
    }
}
