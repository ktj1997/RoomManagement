package com.room.manage.api.room;

import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.service.AuthService;
import com.room.manage.factory.CommonFactory;
import com.room.manage.api.patricipation.service.ParticipationService;
import com.room.manage.api.room.model.dto.DetailRoomInfoDto;
import com.room.manage.api.room.model.entity.Status;
import com.room.manage.api.room.service.RoomService;
import com.room.manage.api.user.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class RoomServiceTest {

    @Autowired
    CommonFactory commonFactory;

    @Autowired
    RoomService roomService;

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
    @DisplayName("Room 정보 반환 테스트")
    void getRoomInfoTest()
    {
        String floor = commonFactory.roomFactory.getFloor();
        String field = commonFactory.roomFactory.getField();
        DetailRoomInfoDto detailRoomInfoDto;

        /**
         * 맨 처음 RoomInfo를 호출 했을 떄, 참여자가 아무도 없어여함 + 초기 상태는 Empty여야함
         */
        detailRoomInfoDto = Assertions.assertDoesNotThrow(() -> roomService.getRoomInfo(floor,field));
        Assertions.assertEquals(0, detailRoomInfoDto.getNowNum());
        Assertions.assertEquals(Status.EMPTY, detailRoomInfoDto.getStatus());
        /**
         * 참여 한 이후에는 참여자가 0명이면 안됨 + 상태가 Activate로 변화되어야함
         */
        //Assertions.assertDoesNotThrow(() ->participationService.joinRoom(commonFactory.participationFactory.getParticipationRequestDto()));
        detailRoomInfoDto = Assertions.assertDoesNotThrow(() -> roomService.getRoomInfo(floor,field));
        Assertions.assertNotEquals(0, detailRoomInfoDto.getNowNum());
        Assertions.assertEquals(Status.ACTIVATE, detailRoomInfoDto.getStatus());
        /**
         * 퇴실이후 다시 0명이 되었을 때, Empty 상태로 변경되어야함
         */
        Assertions.assertDoesNotThrow(() -> participationService.exitRoom(user.getId()));
        detailRoomInfoDto = Assertions.assertDoesNotThrow(() -> roomService.getRoomInfo(floor,field));
        Assertions.assertEquals(0, detailRoomInfoDto.getNowNum());
        Assertions.assertEquals(Status.EMPTY, detailRoomInfoDto.getStatus());

    }


}