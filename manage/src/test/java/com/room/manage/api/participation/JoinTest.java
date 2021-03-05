package com.room.manage.api.participation;

import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.model.dto.SignUpResponseDto;
import com.room.manage.api.participation.exception.AlreadyMaximumParticipantException;
import com.room.manage.api.participation.exception.AlreadyParticipateException;
import com.room.manage.api.participation.exception.InvalidTimeRequestException;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.entity.ParticipationStatus;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.room.model.entity.RoomId;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JoinTest extends IntegrationTest {

    private SignUpResponseDto signUpResponseDto;

    @BeforeEach
    void setUser() {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        signUpResponseDto = authService.signUp(signUpRequestDto);
    }

    @Test
    @Order(1)
    @DisplayName("정상적인 참여 테스트")
    void successfulJoinTest() throws Exception {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-" + "22:59");
        mockMvc.perform(MockMvcRequestBuilders.post("/participation")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participationRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("data.participant").value(commonFactory.userFactory.name))
                .andExpect(MockMvcResultMatchers.jsonPath("data.floor").value(participationRequestDto.getFloor()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.field").value(participationRequestDto.getField()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.participationStatus").value(ParticipationStatus.ACTIVE.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.sleep").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("data.remainSleepNum").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.finishTime").value(participationRequestDto.getFinishTime()));

    }

    @Test
    @DisplayName("이미 참여중이라면 참여 실패")
    void RequestFailedWhenUserAlreadyParticipate() throws Exception {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-" + "22:59");
        assertDoesNotThrow(() -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));
        assertThrows(AlreadyParticipateException.class, () -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));

        mockMvc.perform(MockMvcRequestBuilders.post("/participation")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("참여 요청시간은 08:00~23:00사이여야함")
    void RequestFailedWhenTimeIsExceed() throws Exception {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-" + "23:30");
        assertThrows(InvalidTimeRequestException.class,
                () -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));

        mockMvc.perform(MockMvcRequestBuilders.post("/participation")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("현재 시간보다 과거에 대한 요청은 거부")
    void RequestFailedWhenTimeIsPast() throws Exception {
        /**
         * 테스트 실행시간은 8시 10분 이후라고 가정.
         */
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-" + "08:10");

        assertThrows(InvalidTimeRequestException.class,
                () -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));
        mockMvc.perform(MockMvcRequestBuilders.post("/participation")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("현재인원과 최대참여인원이 같으면, 참여 거부")
    void RequestFailedWhenNowNumIsEqualToMaxNum() throws Exception {
        Room room = roomRepository.findById(new RoomId("3", "B")).get();
        room.setNowNum(room.getMaxNum());
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-" + "22:50");

        assertThrows(AlreadyMaximumParticipantException.class,
                () -> participationService.joinRoom(signUpResponseDto.getId(), participationRequestDto, null));
        mockMvc.perform(MockMvcRequestBuilders.post("/participation")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}
