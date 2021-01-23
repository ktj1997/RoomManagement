package com.room.manage.participation;

import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.service.AuthService;
import com.room.manage.factory.CommonFactory;
import com.room.manage.api.patricipation.exception.AlreadySleepStatusException;
import com.room.manage.api.patricipation.exception.NoParticipationException;
import com.room.manage.api.patricipation.exception.SleepTimeCannotExceedFinishTimeException;
import com.room.manage.api.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.patricipation.model.dto.request.SleepRequestDto;
import com.room.manage.api.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.patricipation.model.dto.response.SleepInfoResponseDto;
import com.room.manage.api.patricipation.model.entity.ParticipationType;
import com.room.manage.api.patricipation.service.ParticipationService;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.service.UserService;
import com.room.manage.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ParticipationServiceTest {

    @Autowired
    CommonFactory commonFactory;

    @Autowired
    ParticipationService participationService;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    private User user;

    @BeforeEach
    void setUser()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        user = authService.signUp(signUpRequestDto);
    }

    @Test
    @DisplayName("참여 테스트")
    void joinTest()
    {
        ParticipationRequestDto participationRequestDto = commonFactory.participationFactory.getParticipationRequestDto();
        ParticipationResponseDto participationResponseDto = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto));

        Assertions.assertEquals(participationResponseDto.getParticipationType(), ParticipationType.ACTIVE);
        Assertions.assertEquals(participationResponseDto.getFloor(),participationRequestDto.getFloor());
        Assertions.assertEquals(participationResponseDto.getField(),participationRequestDto.getField());
        Assertions.assertEquals(participationResponseDto.getRemainSleepNum(),3);
    }

    @Test
    @DisplayName("퇴실 테스트")
    void exitTest()
    {
        ParticipationRequestDto participationRequestDto = commonFactory.participationFactory.getParticipationRequestDto();
        ParticipationResponseDto participationResponseDto = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto));

        Assertions.assertNotNull(participationResponseDto);
        Assertions.assertDoesNotThrow(() -> participationService.exitRoom(user.getId()));
        Assertions.assertThrows(NoParticipationException.class,() -> userService.findMyParticipation(user.getId()));
    }

    @Test
    @DisplayName("부재 테스트")
    void toSleepTest()
    {
        ParticipationRequestDto participationRequestDto = commonFactory.participationFactory.getParticipationRequestDto();
        ParticipationResponseDto participationResponseDto = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto));
        Assertions.assertNull(participationResponseDto.getSleep());


        SleepRequestDto sleepRequestDto = commonFactory.participationFactory.getSleepRequestDto();
        SleepInfoResponseDto sleepInfoResponseDto = null;
        participationResponseDto = userService.findMyParticipation(user.getId());


        if(DateUtil.formatToDate(sleepRequestDto.getDate()).after(DateUtil.formatToDate(participationResponseDto.getFinishTime())))
           Assertions.assertThrows(SleepTimeCannotExceedFinishTimeException.class,() -> participationService.toSleepStatus(user.getId(),sleepRequestDto));
        else
            sleepInfoResponseDto = Assertions.assertDoesNotThrow(() -> participationService.toSleepStatus(user.getId(),sleepRequestDto));

        /**
         * 부재상태에 대한 정보가 Null이 아니여야하며, participation객체의 sleep객체의 상태도 업데이트 되어야함
         */
        Assertions.assertNotNull(sleepInfoResponseDto);
        Assertions.assertEquals(sleepInfoResponseDto.getFinishTime(),participationResponseDto.getFinishTime());
        Assertions.assertEquals(sleepInfoResponseDto.getRemainSleepNum(),participationResponseDto.getRemainSleepNum());
        Assertions.assertEquals(sleepInfoResponseDto.getReason(),sleepRequestDto.getReason());

        /**
         * 부재상태인데 다시 부재요청을 할 경우 오류가 발생해야함
         */
        Assertions.assertThrows(AlreadySleepStatusException.class,() -> participationService.toSleepStatus(user.getId(),sleepRequestDto));
    }

    @Test
    @DisplayName("시간 연장 테스트")
    void extendTimeTest()
    {
        ParticipationRequestDto participationRequestDto = commonFactory.participationFactory.getParticipationRequestDto();
        ParticipationResponseDto participationResponseDto1 = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto));
        ExtendTimeRequestDto extendTimeRequestDto = commonFactory.participationFactory.getExtendTimeRequestDto();
        Assertions.assertDoesNotThrow(() -> participationService.extendTime(user.getId(),extendTimeRequestDto));

        ParticipationResponseDto participationResponseDto2 = userService.findMyParticipation(user.getId());

        /**
         * 시간을 연장하기전 정보와 연장한 후의 정보는 달라야한다.
         */
        Assertions.assertNotEquals(participationResponseDto1.getFinishTime(),participationResponseDto2.getFinishTime());
    }
}

