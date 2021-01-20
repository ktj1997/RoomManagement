package com.room.manage.auth.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApiModel
@Getter
@AllArgsConstructor
public class SignUpRequestDto {

    @ApiModelProperty("아이디")
    private String userName;
    @ApiModelProperty("비밀번호")
    private String password;
    @ApiModelProperty("유저 이름")
    private String name;

}
