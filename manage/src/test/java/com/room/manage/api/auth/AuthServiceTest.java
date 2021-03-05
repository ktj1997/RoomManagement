package com.room.manage.api.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.exception.DuplicateUserNameException;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.user.model.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest extends IntegrationTest {

    @Test
    @DisplayName("정상 회원가입 테스트")
    void SuccessfulSignUpTest() throws Exception {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("data.name").value(signUpRequestDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.userName").value(signUpRequestDto.getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.userRole").value(UserRole.ROLE_USER.toString()));
    }

    @Test
    @DisplayName("중복 닉네임 불가")
    void FailedWhenNameIsDuplicated() throws Exception {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        assertAll(
                () -> authService.signUp(signUpRequestDto),
                () -> assertThrows(DuplicateUserNameException.class, () -> authService.signUp(signUpRequestDto))
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
