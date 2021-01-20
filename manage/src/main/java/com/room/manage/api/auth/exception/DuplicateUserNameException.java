package com.room.manage.api.auth.exception;

public class DuplicateUserNameException extends RuntimeException{
    public DuplicateUserNameException()
    {
        super("중복된 아이디 입니다.");
    }
}
