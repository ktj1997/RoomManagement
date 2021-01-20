package com.room.manage.api.user;

import com.room.manage.api.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

        @ApiOperation("내가 참여중인 스터디룸")
        @GetMapping("/participate")
        public ParticipationResponseDto getParticipation()
        {
            return userService.findMyParticipation();
    }
}
