package com.room.manage.api.participation;

import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.participation.exception.AlreadyMaximumParticipantException;
import com.room.manage.api.participation.exception.AlreadyParticipateException;
import com.room.manage.api.participation.exception.InvalidTimeRequestException;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.participation.model.entity.ParticipationStatus;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.room.model.entity.RoomId;
import com.room.manage.api.user.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class JoinTest extends IntegrationTest {

    private User user = null;

    @BeforeEach
    void setUser()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        user = authService.signUp(signUpRequestDto);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getId(),"", List.of(new SimpleGrantedAuthority(user.getUserRole().toString()))));
    }

    @Test
    @DisplayName("정상적인 참여 테스트")
    void successfulJoinTest()
    {
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3","B",commonFactory.participationFactory.getDay()+"-"+"22:59");
        ParticipationResponseDto participationResponseDto = assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto,null));

        assertEquals(participationResponseDto.getParticipationStatus(), ParticipationStatus.ACTIVE);
        assertEquals(participationResponseDto.getFloor(),participationRequestDto.getFloor());
        assertEquals(participationResponseDto.getField(),participationRequestDto.getField());
        assertEquals(participationResponseDto.getRemainSleepNum(),3);
    }

    @Test
    @DisplayName("이미 참여중이라면 참여 실패")
    void RequestFailedWhenUserAlreadyParticipate(){
        ParticipationRequestDto participationRequestDto =
                new ParticipationRequestDto("3","B",commonFactory.participationFactory.getDay()+"-"+"22:59");
        assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto,null));
        assertThrows(AlreadyParticipateException.class,() -> participationService.joinRoom(participationRequestDto,null));
    }

    @Test
    @DisplayName("참여 요청시간은 08:00~23:00사이여야함")
    void RequestFailedWhenTimeIsExceed()
    {
        ParticipationRequestDto participationRequestDto=
                new ParticipationRequestDto("3","B",commonFactory.participationFactory.getDay()+"-"+"23:30");
        assertThrows(InvalidTimeRequestException.class,() -> participationService.joinRoom(participationRequestDto,null));
    }

    @Test
    @DisplayName("현재 시간보다 과거에 대한 요청은 거부")
    void RequestFailedWhenTimeIsPast()
    {
        /**
         * 테스트 실행시간은 8시 10분 이후라고 가정.
         */
        ParticipationRequestDto participationRequestDto=
                new ParticipationRequestDto("3","B",commonFactory.participationFactory.getDay()+"-"+"08:10");

        assertThrows(InvalidTimeRequestException.class,() -> participationService.joinRoom(participationRequestDto,null));
    }

    @Test
    @DisplayName("현재인원과 최대참여인원이 같으면, 참여 거부")
    void RequestFailedWhenNowNumIsEqualToMaxNum()
    {
        Room room = roomRepository.findById(new RoomId("3","B")).get();
        room.setNowNum(room.getMaxNum());
        ParticipationRequestDto participationRequestDto=
                new ParticipationRequestDto("3","B",commonFactory.participationFactory.getDay()+"-"+"22:50");

        assertThrows(AlreadyMaximumParticipantException.class,()->participationService.joinRoom(participationRequestDto,null));

    }

}
