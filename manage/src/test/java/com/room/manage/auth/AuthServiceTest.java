package com.room.manage.auth;

import com.room.manage.auth.model.dto.SignUpRequestDto;
import com.room.manage.auth.service.AuthService;
import com.room.manage.factory.CommonFactory;
import com.room.manage.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    CommonFactory commonFactory;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void signUpTest()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        Assertions.assertAll(
                () -> authService.signUp(signUpRequestDto),
                () -> Assertions.assertNotEquals(0,userRepository.findAll().size())
        );
    }
}
