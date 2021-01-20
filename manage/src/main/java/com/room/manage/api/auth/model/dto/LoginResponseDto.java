package com.room.manage.api.auth.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApiModel
@Getter
public class LoginResponseDto {

    @ApiModelProperty("AccessToken")
    private String accessToken;
    @ApiModelProperty("RefreshToken")
    private String refreshToken;

    public LoginResponseDto(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
