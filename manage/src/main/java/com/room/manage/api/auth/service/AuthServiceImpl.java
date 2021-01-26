package com.room.manage.api.auth.service;

import com.room.manage.api.auth.exception.DuplicateUserNameException;
import com.room.manage.api.auth.exception.WrongLoginInfoException;
import com.room.manage.api.auth.model.dto.LogInRequestDto;
import com.room.manage.api.auth.model.dto.LoginResponseDto;
import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.model.entity.UserRole;
import com.room.manage.api.user.repository.UserRepository;
import com.room.manage.core.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signUp(SignUpRequestDto signUpRequestDto) {

        if(userRepository.existsByUserName(signUpRequestDto.getUserName()))
            throw new DuplicateUserNameException();

        User user = User.builder()
                .userName(signUpRequestDto.getUserName())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .name(signUpRequestDto.getName())
                .userRole(UserRole.ROLE_USER)
                .build();
       return userRepository.save(user);
    }

    @Override
    public LoginResponseDto signin(LogInRequestDto logInRequestDto) {
        User user = userRepository.findByUserName(logInRequestDto.getUserName()).orElseThrow(WrongLoginInfoException::new);

        if(!passwordEncoder.matches(logInRequestDto.getPassword(),user.getPassword()))
            throw new WrongLoginInfoException();
        return new LoginResponseDto(jwtProvider.generateAccessToken(user.getId(),user.getUserRole()));
    }
}
