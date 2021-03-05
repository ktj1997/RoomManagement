package com.room.manage.api.user;

import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.model.dto.SignUpResponseDto;
import com.room.manage.api.auth.service.AuthService;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import com.room.manage.factory.CommonFactory;
import com.room.manage.api.participation.exception.NoParticipationException;
import com.room.manage.api.participation.service.ParticipationService;
import com.room.manage.api.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest extends IntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    CommonFactory commonFactory;

    @Autowired
    AuthService authService;

    @Autowired
    ParticipationService participationService;

    private SignUpResponseDto signUpResponseDto;

    @BeforeEach
    void setUser() {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        signUpResponseDto = authService.signUp(signUpRequestDto);
    }


    @Test
    @DisplayName("유저의 참여정보 리턴 테스트")
    public void findParticipationTest() throws Exception {
        assertThrows(NoParticipationException.class, () -> userService.findMyParticipation(signUpResponseDto.getId()));
        assertDoesNotThrow(() -> participationService.joinRoom(signUpResponseDto.getId(), new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:50"), null));
        ParticipationResponseDto participationResponseDto = assertDoesNotThrow(() -> userService.findMyParticipation(signUpResponseDto.getId()));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/participation")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("data.floor").value(participationResponseDto.getFloor()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.field").value(participationResponseDto.getField()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.startTime").value(participationResponseDto.getStartTime()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.finishTime").value(participationResponseDto.getFinishTime()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.remainSleepNum").value(participationResponseDto.getRemainSleepNum()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.participationStatus").value(participationResponseDto.getParticipationStatus().toString()));
    }

    /**
     * 참여중이 아니라면 테스트 실패
     */
    @Test
    void FailedWhenUserNotParticipate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/participation")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
