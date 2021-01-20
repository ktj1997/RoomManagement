package com.room.manage.api.room.exception;


public class AlreadyMaximumParticipantException extends RuntimeException {
    public AlreadyMaximumParticipantException()
    {
        super("이미 최대 인원입니다.");
    }
}