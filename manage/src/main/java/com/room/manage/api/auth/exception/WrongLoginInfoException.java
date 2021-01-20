package com.room.manage.api.auth.exception;

public class WrongLoginInfoException extends RuntimeException{
    public WrongLoginInfoException()
    {
        super("잘못된 로그인 정보입니다.");
    }
}
