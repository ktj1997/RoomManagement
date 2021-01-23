package com.room.manage.core.Exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@ToString
public class ExceptionResponse {
    String message;
    String code;
    int httpStatus;
    Date timeStamp = new Date();

    public ExceptionResponse(HttpStatus status,ExceptionCode code)
    {
        this.message = code.getMessage();
        this.code = code.getCode();
        this.httpStatus = status.value();
    }
}
