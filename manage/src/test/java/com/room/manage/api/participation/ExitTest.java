package com.room.manage.api.participation;

import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.model.dto.SignUpResponseDto;
import com.room.manage.api.participation.exception.NoParticipationException;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

public class ExitTest extends IntegrationTest {

    private SignUpResponseDto signUpResponseDto;

    @BeforeEach
    void setUser()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        signUpResponseDto = authService.signUp(signUpRequestDto);
    }
    @Test
    @DisplayName("정상적인 퇴실 테스트")
    void SuccessFulExitTest() throws Exception {
        ParticipationRequestDto participationRequestDto
                = new ParticipationRequestDto("3","B",commonFactory.participationFactory.getDay()+"-22:50");
        ParticipationResponseDto participationResponseDto = assertDoesNotThrow(() -> participationService.joinRoom(signUpResponseDto.getId(),participationRequestDto,null));
        assertNotNull(participationResponseDto);


        mockMvc.perform(MockMvcRequestBuilders.delete("/participation")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("data.participant").value(signUpResponseDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.startTime").value(participationResponseDto.getStartTime()));
    }

    @Test
    @DisplayName("참여정보가 없는데 퇴실요청시 거부")
    void RequestFailedWhenDoesNotParticipate() throws Exception {
        assertThrows(NoParticipationException.class,() -> participationService.exitRoom(signUpResponseDto.getId()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/participation")
                .header("Authorization",accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
