package com.room.manage.patricipation.exception;

public class NoSuchParticipationException extends RuntimeException{
    public NoSuchParticipationException()
    {
        super("해당하는 참가정보가 존재하지 않습니다.");
    }
}
