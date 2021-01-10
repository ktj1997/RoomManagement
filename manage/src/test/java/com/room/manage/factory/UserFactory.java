package com.room.manage.factory;

import com.room.manage.auth.model.dto.SignUpRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    private String userName = "ktj7916";
    private String name = "kim";
    private String password ="1q2w3e4r!!";


    public SignUpRequestDto getSignUpRequestDto()
    {
        return new SignUpRequestDto(userName,password,name);
    }
}
