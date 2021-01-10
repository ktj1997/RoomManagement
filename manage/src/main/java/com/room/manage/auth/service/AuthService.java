package com.room.manage.auth.service;

import com.room.manage.auth.model.dto.SignUpRequestDto;
import com.room.manage.user.model.entity.User;

public interface AuthService {
    User signUp(SignUpRequestDto signUpRequestDto);
}
