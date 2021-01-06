package com.room.manage.auth.service;

import com.room.manage.auth.model.dto.SignUpRequestDto;
import com.room.manage.user.model.entity.User;
import com.room.manage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        User user = User.builder()
                .userName(signUpRequestDto.getUserName())
                .password(signUpRequestDto.getPassword())
                .name(signUpRequestDto.getName()).build();

        userRepository.save(user);
    }
}
