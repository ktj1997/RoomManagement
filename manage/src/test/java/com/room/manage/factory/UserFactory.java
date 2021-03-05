package com.room.manage.factory;

import com.room.manage.api.auth.model.dto.SignUpRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public String userName = "ktj7916";
    public String name = "김태준";
    private String password ="1q2w3e4r!!";


    public SignUpRequestDto getSignUpRequestDto()
    {
        return new SignUpRequestDto(userName,password,name);
    }
}
