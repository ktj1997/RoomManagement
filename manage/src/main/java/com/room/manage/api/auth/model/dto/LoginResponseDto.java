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
    public LoginResponseDto(String accessToken){
        this.accessToken = accessToken;
    }
}
