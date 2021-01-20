package com.room.manage.api.patricipation.exception;

public class InvalidTimeRequestException extends RuntimeException{
    public InvalidTimeRequestException()
    {
        super("유효하지 않은 시간요청입니다.");
    }
}
