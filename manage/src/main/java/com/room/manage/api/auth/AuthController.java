package com.room.manage.api.auth;

import com.room.manage.api.auth.model.dto.LogInRequestDto;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.model.dto.LoginResponseDto;
import com.room.manage.api.auth.service.AuthService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("회원가입")
    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpRequestDto signUpRequestDto)
    {
         authService.signUp(signUpRequestDto);
    }

    @ApiOperation("로그인")
    @PostMapping("/login")
    public LoginResponseDto loginResponseDto(@RequestBody LogInRequestDto logInRequestDto){
        return authService.login(logInRequestDto);
    }
}
