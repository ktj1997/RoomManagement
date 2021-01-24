package com.room.manage.participation;

import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.service.AuthService;
import com.room.manage.api.patricipation.exception.InvalidTimeRequestException;
import com.room.manage.api.patricipation.model.entity.Participation;
import com.room.manage.factory.CommonFactory;
import com.room.manage.api.patricipation.exception.AlreadySleepStatusException;
import com.room.manage.api.patricipation.exception.NoParticipationException;
import com.room.manage.api.patricipation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.patricipation.model.dto.request.SleepRequestDto;
import com.room.manage.api.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.patricipation.model.entity.ParticipationStatus;
import com.room.manage.api.patricipation.service.ParticipationService;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.service.UserService;
import com.room.manage.core.util.DateUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @BeforeAll
    void setUser()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        user = authService.signUp(signUpRequestDto);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getId(),"", List.of(new SimpleGrantedAuthority(user.getUserRole().toString()))));
    }

    @Test
    @Order(1)
    @DisplayName("참여 테스트")
    void joinTest()
    {
        ParticipationRequestDto participationRequestDto = commonFactory.participationFactory.getParticipationRequestDto();
        ParticipationResponseDto participationResponseDto = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto));

        Assertions.assertEquals(participationResponseDto.getParticipationStatus(), ParticipationStatus.ACTIVE);
        Assertions.assertEquals(participationResponseDto.getFloor(),participationRequestDto.getFloor());
        Assertions.assertEquals(participationResponseDto.getField(),participationRequestDto.getField());
        Assertions.assertEquals(participationResponseDto.getRemainSleepNum(),3);
    }

    @Test
    @Disabled
    @DisplayName("퇴실 테스트")
    void exitTest()
    {
        ParticipationRequestDto participationRequestDto = commonFactory.participationFactory.getParticipationRequestDto();
        ParticipationResponseDto participationResponseDto = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto));

        Assertions.assertNotNull(participationResponseDto);
        participationService.exitRoom(user.getId());
        Assertions.assertThrows(NoParticipationException.class,() -> userService.findMyParticipation());
    }

    @Test
    @Order(2)
    @DisplayName("부재 테스트")
    void toSleepTest()
    {
        ParticipationRequestDto participationRequestDto = commonFactory.participationFactory.getParticipationRequestDto();
        ParticipationResponseDto participationResponseDto = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto));
        ParticipationResponseDto participationResponseDtoAfterSleep = null;
        Assertions.assertNull(participationResponseDto.getSleep());


        SleepRequestDto sleepRequestDto = commonFactory.participationFactory.getSleepRequestDto();
        participationResponseDto = userService.findMyParticipation();


        if(DateUtil.formatToDate(sleepRequestDto.getDate()).after(DateUtil.formatToDate(participationResponseDto.getFinishTime())))
           Assertions.assertThrows(InvalidTimeRequestException.class,() -> participationService.toSleepStatus(sleepRequestDto));
        else
            participationResponseDtoAfterSleep = Assertions.assertDoesNotThrow(() -> participationService.toSleepStatus(sleepRequestDto));

        /**
         * 부재상태에 대한 정보가 Null이 아니여야하며, participation객체의 sleep객체의 상태도 업데이트 되어야함
         */
        Assertions.assertNotNull(participationResponseDtoAfterSleep);
        Assertions.assertEquals(participationResponseDtoAfterSleep.getFinishTime(),participationResponseDto.getFinishTime());
        Assertions.assertNotEquals(participationResponseDtoAfterSleep.getRemainSleepNum(),participationResponseDto.getRemainSleepNum());
        Assertions.assertEquals(participationResponseDtoAfterSleep.getSleep().getSleepReason(),sleepRequestDto.getReason());

        /**
         * 부재상태인데 다시 부재요청을 할 경우 오류가 발생해야함
         */
        Assertions.assertThrows(AlreadySleepStatusException.class,() -> participationService.toSleepStatus(sleepRequestDto));
    }

    @Test
    @Order(3)
    @DisplayName("시간 연장 테스트")
    void extendTimeTest()
    {
        ParticipationResponseDto participationResponseDto1 = userService.findMyParticipation();
        ExtendTimeRequestDto extendTimeRequestDto = commonFactory.participationFactory.getExtendTimeRequestDto();
        Assertions.assertDoesNotThrow(() -> participationService.extendTime(extendTimeRequestDto));

        ParticipationResponseDto participationResponseDto2 = userService.findMyParticipation();

        /**
         * 시간을 연장하기전 정보와 연장한 후의 정보는 달라야한다.
         */
        Assertions.assertNotEquals(participationResponseDto1.getFinishTime(),participationResponseDto2.getFinishTime());
    }
}

