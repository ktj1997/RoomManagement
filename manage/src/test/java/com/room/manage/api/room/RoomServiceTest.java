package com.room.manage.api.room;

import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.model.dto.SignUpResponseDto;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.room.exception.RoomNotExistException;
import com.room.manage.api.room.model.dto.DetailRoomInfoDto;
import com.room.manage.api.room.model.entity.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTest extends IntegrationTest {

    private SignUpResponseDto signUpResponseDto;

    @BeforeEach
    void setUser() {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        signUpResponseDto = authService.signUp(signUpRequestDto);
    }

    @Test
    @DisplayName("Room 정보 반환 테스트")
    void getRoomInfoTest() throws Exception {
        String floor = "3";
        String field = "B";
        DetailRoomInfoDto detailRoomInfoDto;

        /**
         * 맨 처음 RoomInfo를 호출 했을 떄, 참여자가 아무도 없어여함 + 초기 상태는 Empty여야함
         */
        detailRoomInfoDto = Assertions.assertDoesNotThrow(() -> roomService.getRoomInfo(floor, field));
        Assertions.assertEquals(0, detailRoomInfoDto.getNowNum());
        Assertions.assertEquals(Status.EMPTY, detailRoomInfoDto.getStatus());
        /**
         * 참여 한 이후에는 참여자가 0명이면 안됨 + 상태가 Activate로 변화되어야함
         */
        assertDoesNotThrow(() -> participationService.joinRoom(signUpResponseDto.getId(), new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:50"), null));
        detailRoomInfoDto = Assertions.assertDoesNotThrow(() -> roomService.getRoomInfo(floor, field));
        Assertions.assertNotEquals(0, detailRoomInfoDto.getNowNum());
        Assertions.assertEquals(Status.ACTIVATE, detailRoomInfoDto.getStatus());
        /**
         * 퇴실이후 다시 0명이 되었을 때, Empty 상태로 변경되어야함
         */
        Assertions.assertDoesNotThrow(() -> participationService.exitRoom(signUpResponseDto.getId()));
        detailRoomInfoDto = Assertions.assertDoesNotThrow(() -> roomService.getRoomInfo(floor, field));
        Assertions.assertEquals(0, detailRoomInfoDto.getNowNum());
        Assertions.assertEquals(Status.EMPTY, detailRoomInfoDto.getStatus());

        mockMvc.perform(MockMvcRequestBuilders.get("/rooms/room")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .param("floor", floor)
                .param("field", field))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("data.floor").value(floor))
                .andExpect(MockMvcResultMatchers.jsonPath("data.field").value(field))
                .andExpect(MockMvcResultMatchers.jsonPath("data.maxNum").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.nowNum").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.status").value(Status.EMPTY.toString()));
    }

    @Test
    @DisplayName("잘못된 층,호수 입력시 에러")
    void FailedWhenRoomInfoInvalid() throws Exception {
        String floor = "3";
        String field = "D";
        assertThrows(RoomNotExistException.class, () -> roomService.getRoomInfo(floor, field));

        mockMvc.perform(MockMvcRequestBuilders.get("/rooms/room")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .param("floor", floor)
                .param("field", field))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}