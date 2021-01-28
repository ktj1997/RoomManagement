package com.room.manage.api.participation;

import com.room.manage.api.IntegrationTest;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.patricipation.exception.AlarmExecutionException;
import com.room.manage.api.patricipation.exception.NoParticipationException;
import com.room.manage.api.patricipation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.user.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ExitTest extends IntegrationTest {

    private User user;

    @BeforeEach
    void setUser()
    {
        SignUpRequestDto signUpRequestDto = commonFactory.userFactory.getSignUpRequestDto();
        user = authService.signUp(signUpRequestDto);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getId(),"", List.of(new SimpleGrantedAuthority(user.getUserRole().toString()))));
    }


    @Test
    @DisplayName("정상적인 퇴실 테스트")
    void SuccessFulExitTest()
    {
        ParticipationRequestDto participationRequestDto
                = new ParticipationRequestDto("3","B",commonFactory.participationFactory.getDay()+"-22:50");
        ParticipationResponseDto participationResponseDto = assertDoesNotThrow(() -> participationService.joinRoom(participationRequestDto,null));
        assertNotNull(participationResponseDto);
        /**
         * 테스트시 소켓서버 연결(x)상황
         */
        assertThrows(AlarmExecutionException.class,() -> participationService.exitRoom(user.getId()));
        assertThrows(NoParticipationException.class,() -> userService.findMyParticipation());
    }

    @Test
    @DisplayName("참여정보가 없는데 퇴실요청시 거부")
    void RequestFailedWhenDoesNotParticipate()
    {
        assertThrows(NoParticipationException.class,() -> participationService.exitRoom(user.getId()));
    }
}
