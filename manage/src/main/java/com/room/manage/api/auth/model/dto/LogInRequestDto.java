package com.room.manage.api.auth.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
@Getter
@ApiModel("로그인 요청 Dto")
public class LogInRequestDto {

    @ApiModelProperty("유저 아이디")
    private String userName;
    @ApiModelProperty("비밀번호")
    private String password;

}
