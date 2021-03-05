package com.room.manage.api.participation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.model.dto.SignUpResponseDto;
import com.room.manage.api.participation.exception.InvalidTimeRequestException;
import com.room.manage.api.participation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.user.model.entity.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ExtendTimeTest extends IntegrationTest {

    private SignUpResponseDto signUpResponseDto;
    private String accessToken;

    @BeforeEach
    void setUser() throws JsonProcessingException {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        signUpResponseDto = authService.signUp(signUpRequestDto);
        accessToken = "Bearer " + jwtProvider.generateAccessToken(signUpResponseDto.getId(),Enum.valueOf(UserRole.class,signUpResponseDto.getUserRole()));
    }

    @Test
    @DisplayName("정상적인 시간 연장 테스트")
    void extendTimeTest() throws Exception {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:30");
        ParticipationResponseDto participationResponseDto1 = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));
        ExtendTimeRequestDto extendTimeRequestDto1 = new ExtendTimeRequestDto(commonFactory.participationFactory.getDay() + "-22:40");
        ExtendTimeRequestDto extendTimeRequestDto2 = new ExtendTimeRequestDto(commonFactory.participationFactory.getDay() + "-22:50");
        ParticipationResponseDto participationResponseDto2 = Assertions.assertDoesNotThrow(() -> participationService.extendTime(signUpResponseDto.getId(), extendTimeRequestDto1));

        /**
         * 시간을 연장하기전 정보와 연장한 후의 정보는 달라야한다.
         */
        Assertions.assertNotEquals(participationResponseDto1.getFinishTime(), participationResponseDto2.getFinishTime());

        mockMvc.perform(MockMvcRequestBuilders.put("/participation/extend")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(extendTimeRequestDto2)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("data.participant").value(signUpResponseDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.finishTime").value(extendTimeRequestDto2.getFinishTime()));
    }

    @Test
    @DisplayName("연장 요청시간이 전체이용 종료시간보다 과거일 수 없음")
    void RequestFailedWhenExtendTimeRequestIsBeforeThanFinishTime() throws Exception {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:40");
        ParticipationResponseDto participationResponseDto1 = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));
        ExtendTimeRequestDto extendTimeRequestDto = new ExtendTimeRequestDto(commonFactory.participationFactory.getDay() + "-22:20");
        Assertions.assertThrows(InvalidTimeRequestException.class, () -> participationService.extendTime(signUpResponseDto.getId(), extendTimeRequestDto));

        mockMvc.perform(MockMvcRequestBuilders.put("/participation/extend")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(extendTimeRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
