package com.room.manage.api.patricipation.exception;

public class SleepRequestDenyException extends RuntimeException{
    public SleepRequestDenyException()
    {
        super("남은 부재가능 횟수가 없습니다.");
    }
}
