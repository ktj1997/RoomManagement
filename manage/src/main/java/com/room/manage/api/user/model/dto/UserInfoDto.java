package com.room.manage.api.user.model.dto;

import com.room.manage.api.user.model.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApiModel
@Getter
@AllArgsConstructor
public class UserInfoDto
{
    /**
     * 식별자
     */
    @ApiModelProperty("유저 식별자")
    private Long id;
    /**
     *  이름
     */
    @ApiModelProperty("유저 이름")
    private String name;

    public UserInfoDto(User user){
        this.id = user.getId();
        this.name = user.getName();
    }
}
