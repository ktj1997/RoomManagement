package com.room.manage.api.auth.service;

import com.room.manage.api.auth.model.dto.LogInRequestDto;
import com.room.manage.api.auth.model.dto.LoginResponseDto;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.auth.model.dto.SignUpResponseDto;
import com.room.manage.api.user.model.entity.User;

public interface AuthService {
    SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto);
    LoginResponseDto signin(LogInRequestDto logInRequestDto);

}
