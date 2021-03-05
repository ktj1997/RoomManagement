package com.room.manage.api.participation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.model.dto.SignUpResponseDto;
import com.room.manage.api.participation.exception.AlreadySleepStatusException;
import com.room.manage.api.participation.exception.InvalidTimeRequestException;
import com.room.manage.api.participation.exception.RemainSleepNumZeroException;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.dto.request.SleepRequestDto;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.api.participation.model.entity.SleepReason;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.model.entity.UserRole;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTest extends IntegrationTest {

    private SignUpResponseDto signUpResponseDto;
    private String accessToken;

    @BeforeEach
    void setUser() throws JsonProcessingException {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        signUpResponseDto = authService.signUp(signUpRequestDto);
        accessToken = "Bearer " + jwtProvider.generateAccessToken(signUpResponseDto.getId(),Enum.valueOf(UserRole.class,signUpResponseDto.getUserRole()));
    }

    @Test
    @DisplayName("성공적인 부재 테스트")
    void toSleepTest() throws Exception {
        SleepRequestDto sleepRequestDto = new SleepRequestDto(commonFactory.participationFactory.getDay() + "-22:40", SleepReason.TOILET);
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:50");
        Assertions.assertDoesNotThrow(() -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));
        mockMvc.perform(MockMvcRequestBuilders.put("/participation/sleep")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sleepRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("data.sleep").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("data.sleep.finishTime").value(sleepRequestDto.getDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.sleep.sleepReason").value(sleepRequestDto.getReason().toString()));
    }

    @Test
    @DisplayName("부재 요청시간이 이용종료시간을 넘어가면 안됨")
    void RequestFailedWhenTimeIsExceed() throws Exception {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:40");
        Assertions.assertDoesNotThrow(() -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));
        SleepRequestDto sleepRequestDto = new SleepRequestDto(commonFactory.participationFactory.getDay() + "-22:50", SleepReason.TOILET);
        assertThrows(InvalidTimeRequestException.class, () -> participationService.toSleepStatus(signUpResponseDto.getId(), sleepRequestDto));

        mockMvc.perform(MockMvcRequestBuilders.put("/participation/sleep")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sleepRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("남은 부재요청횟수가 0인데, 부재신청을 할 수 없음")
    void RequestFailedWhenRemainSleepNumIsZero() throws Exception {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:50");
        Assertions.assertDoesNotThrow(() -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));
        User user = userRepository.findById(signUpResponseDto.getId()).get();
        Participation participation = participationRepository.findByParticipant(user).get();
        participation.setRemainSleepNum(0);

        SleepRequestDto sleepRequestDto = new SleepRequestDto(commonFactory.participationFactory.getDay() + "-22:50", SleepReason.TOILET);
        assertThrows(RemainSleepNumZeroException.class, () -> participationService.toSleepStatus(user.getId(), sleepRequestDto));

        mockMvc.perform(MockMvcRequestBuilders.put("/participation/sleep")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sleepRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("이미 부재중인데, 부재요청을 할 수없음")
    void RequestFailedWhenUserIsAlreadySleepStatus() throws Exception {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:50");
        Assertions.assertDoesNotThrow(() -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));

        SleepRequestDto sleepRequestDto = new SleepRequestDto(commonFactory.participationFactory.getDay() + "-22:40", SleepReason.TOILET);
        assertDoesNotThrow(() -> participationService.toSleepStatus(signUpResponseDto.getId(), sleepRequestDto));
        assertThrows(AlreadySleepStatusException.class, () -> participationService.toSleepStatus(signUpResponseDto.getId(), sleepRequestDto));

        mockMvc.perform(MockMvcRequestBuilders.put("/participation/sleep")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sleepRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
