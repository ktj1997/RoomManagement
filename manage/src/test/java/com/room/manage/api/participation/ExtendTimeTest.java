package com.room.manage.api.participation;

import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.participation.exception.InvalidTimeRequestException;
import com.room.manage.api.participation.model.dto.request.ExtendTimeRequestDto;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.user.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class ExtendTimeTest extends IntegrationTest {

    private User user;

    @BeforeEach
    void setUser()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        user = authService.signUp(signUpRequestDto);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getId(),"", List.of(new SimpleGrantedAuthority(user.getUserRole().toString()))));
    }

    @Test
    @DisplayName("정상적인 시간 연장 테스트")
    void extendTimeTest()
    {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3","B",commonFactory.participationFactory.getDay()+"-22:40");
        ParticipationResponseDto participationResponseDto1 = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto,null));
        ExtendTimeRequestDto extendTimeRequestDto = new ExtendTimeRequestDto(commonFactory.participationFactory.getDay()+"-22:50");
        ParticipationResponseDto participationResponseDto2 = Assertions.assertDoesNotThrow(() -> participationService.extendTime(extendTimeRequestDto));

        /**
         * 시간을 연장하기전 정보와 연장한 후의 정보는 달라야한다.
         */
        Assertions.assertNotEquals(participationResponseDto1.getFinishTime(),participationResponseDto2.getFinishTime());
    }

    @Test
    @DisplayName("연장 요청시간이 전체이용 종료시간보다 과거일 수 없음")
    void RequestFailedWhenExtendTimeRequestIsBeforeThanFinishTime(){
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3","B",commonFactory.participationFactory.getDay()+"-22:40");
        ParticipationResponseDto participationResponseDto1 = Assertions.assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto,null));
        ExtendTimeRequestDto extendTimeRequestDto = new ExtendTimeRequestDto(commonFactory.participationFactory.getDay()+"-22:20");
       Assertions.assertThrows(InvalidTimeRequestException.class,() -> participationService.extendTime(extendTimeRequestDto));

    }
}
