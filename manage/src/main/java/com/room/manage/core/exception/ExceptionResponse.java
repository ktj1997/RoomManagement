package com.room.manage.core.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@ToString
public class ExceptionResponse {
    String message;
    String code;
    Date timeStamp = new Date();

    public ExceptionResponse(ExceptionCode code) {
        this.message = code.getMessage();
        this.code = code.getCode();
    }
}
