package com.room.manage.auth;

import com.room.manage.auth.model.dto.SignUpRequestDto;
import com.room.manage.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpRequestDto signUpRequestDto)
    {
        authService.signUp(signUpRequestDto);
    }
}
