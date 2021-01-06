package com.room.manage.auth.service;

import com.room.manage.auth.model.dto.SignUpRequestDto;

public interface AuthService {
    void signUp(SignUpRequestDto signUpRequestDto);
}
