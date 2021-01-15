package com.room.manage.user;

import com.room.manage.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.user.service.UserService;
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

    @GetMapping("/participate")
    public ParticipationResponseDto getParticipation(@RequestParam Long userId)
    {
        return userService.findMyParticipation(userId);
    }
}
