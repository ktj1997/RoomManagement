package com.room.manage.api.auth.service;

import com.room.manage.api.auth.model.dto.LogInRequestDto;
import com.room.manage.api.auth.model.dto.LoginResponseDto;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;

public interface AuthService {
    void signUp(SignUpRequestDto signUpRequestDto);
    LoginResponseDto login(LogInRequestDto logInRequestDto);

}
