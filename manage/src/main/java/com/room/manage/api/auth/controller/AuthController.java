package com.room.manage.api.auth.controller;

import com.room.manage.api.auth.model.dto.LogInRequestDto;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.model.dto.LoginResponseDto;
import com.room.manage.api.auth.service.AuthService;
import com.room.manage.api.common.Response;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation("회원가입")
    @PostMapping("/signup")
    public Response signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return new Response(HttpStatus.CREATED, authService.signUp(signUpRequestDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("로그인")
    @PostMapping("/login")
    public Response loginResponseDto(@RequestBody LogInRequestDto logInRequestDto) {
        return new Response(HttpStatus.OK, authService.signin(logInRequestDto));
    }
}
