package com.room.manage.api.patricipation.exception;


public class AlreadySleepStatusException extends RuntimeException{
    public AlreadySleepStatusException()
    {
        super("이미 부재 상태입니다.");
    }
}
