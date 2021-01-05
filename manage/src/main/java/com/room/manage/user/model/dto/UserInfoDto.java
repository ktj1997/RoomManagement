package com.room.manage.user.model.dto;

import com.room.manage.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto
{
    /**
     * 식별자
     */
    private Long id;
    /**
     *  이름
     */
    private String name;

    public UserInfoDto(User user){
        this.id = user.getId();
        this.name = user.getName();
    }
}
