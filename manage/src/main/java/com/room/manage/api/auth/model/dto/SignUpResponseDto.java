package com.room.manage.api.auth.model.dto;

import com.room.manage.api.user.model.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("회원가입 결과")
public class SignUpResponseDto {
    Long id;
    String name;
    String userName;
    String userRole;

    public SignUpResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.userName = user.getUserName();
        this.userRole = user.getUserRole().toString();
    }
}
