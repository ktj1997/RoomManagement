package com.room.manage.api.auth;

import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.exception.DuplicateUserNameException;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest extends IntegrationTest {

    @Test
    @DisplayName("정상 회원가입 테스트")
    void SuccessfulSignUpTest()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        assertAll(
                () -> authService.signUp(signUpRequestDto),
                () -> assertNotEquals(0,userRepository.findAll().size())
        );
    }

    @Test
    @DisplayName("중복 닉네임 불가")
    void FailedWhenNameIsDuplicated()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        assertAll(
                () -> authService.signUp(signUpRequestDto),
                () -> assertThrows(DuplicateUserNameException.class,() -> authService.signUp(signUpRequestDto))
        );
    }
}
