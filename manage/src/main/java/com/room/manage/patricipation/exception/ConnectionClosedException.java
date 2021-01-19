package com.room.manage.patricipation.exception;

import javax.persistence.Converter;

public class ConnectionClosedException extends RuntimeException {
    public ConnectionClosedException()
    {
        super("비정상적인 연결종료로, Socket 종료 명령이 정상적으로 실행되지 않았습니다.");
    }
}
