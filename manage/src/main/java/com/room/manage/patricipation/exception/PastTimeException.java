package com.room.manage.patricipation.exception;

public class PastTimeException extends RuntimeException {
    public PastTimeException()
    {
        super("과거의 시간을 종료시간을 설정 할 수 없습니다.");
    }
}
