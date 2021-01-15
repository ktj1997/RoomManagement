package com.room.manage.patricipation.exception;

public class AlreadyParticipateException extends RuntimeException{
    public AlreadyParticipateException()
    {
        super("이미 참여중입니다.");
    }
}
