package com.room.manage.api.participation;

import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.participation.exception.AlreadySleepStatusException;
import com.room.manage.api.participation.exception.InvalidTimeRequestException;
import com.room.manage.api.participation.exception.RemainSleepNumZeroException;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.dto.request.SleepRequestDto;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.api.participation.model.entity.SleepReason;
import com.room.manage.api.user.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SleepTest extends IntegrationTest {

    private User user = null;

    @BeforeEach
    void setUser()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        user = authService.signUp(signUpRequestDto);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getId(),"", List.of(new SimpleGrantedAuthority(user.getUserRole().toString()))));
    }

    @Test
    @DisplayName("성공적인 부재 테스트")
    void toSleepTest() {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:50");
        ParticipationResponseDto participationResponseDto = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto,null));
        ParticipationResponseDto participationResponseDtoAfterSleep = null;

        SleepRequestDto sleepRequestDto = new SleepRequestDto(commonFactory.participationFactory.getDay()+"-22:40", SleepReason.TOILET);
        participationResponseDtoAfterSleep = assertDoesNotThrow(() -> participationService.toSleepStatus(sleepRequestDto));

        assertNull(participationResponseDto.getSleep());
        assertNotNull(participationResponseDtoAfterSleep.getSleep());
        assertNotEquals(participationResponseDto.getRemainSleepNum(),participationResponseDtoAfterSleep.getRemainSleepNum());
        assertNotEquals(participationResponseDto.getParticipationStatus(),participationResponseDtoAfterSleep.getParticipationStatus());
    }

    @Test
    @DisplayName("부재 요청시간이 이용종료시간을 넘어가면 안됨")
    void RequestFailedWhenTimeIsExceed(){
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:40");
        Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto,null));
        SleepRequestDto sleepRequestDto = new SleepRequestDto(commonFactory.participationFactory.getDay()+"-22:50", SleepReason.TOILET);
        assertThrows(InvalidTimeRequestException.class,() -> participationService.toSleepStatus(sleepRequestDto));
    }

    @Test
    @DisplayName("남은 부재요청횟수가 0인데, 부재신청을 할 수 없음")
    void RequestFailedWhenRemainSleepNumIsZero()
    {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:50");
        Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto,null));
        Participation participation = participationRepository.findByParticipant(user).get();
        participation.setRemainSleepNum(0);

        SleepRequestDto sleepRequestDto = new SleepRequestDto(commonFactory.participationFactory.getDay()+"-22:50", SleepReason.TOILET);
        assertThrows(RemainSleepNumZeroException.class,() -> participationService.toSleepStatus(sleepRequestDto));
    }

    @Test
    @DisplayName("이미 부재중인데, 부재요청을 할 수없음")
    void RequestFailedWhenUserIsAlreadySleepStatus()
    {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3", "B", commonFactory.participationFactory.getDay() + "-22:50");
        Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto,null));

        SleepRequestDto sleepRequestDto = new SleepRequestDto(commonFactory.participationFactory.getDay()+"-22:40", SleepReason.TOILET);
        assertDoesNotThrow(() -> participationService.toSleepStatus(sleepRequestDto));
        assertThrows(AlreadySleepStatusException.class,() -> participationService.toSleepStatus(sleepRequestDto));
    }

}
