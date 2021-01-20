package com.room.manage.api.patricipation.exception;

public class SleepTimeCannotExceedFinishTimeException extends RuntimeException{
    public SleepTimeCannotExceedFinishTimeException()
    {
        super("종료시간을 넘어서 부재시간을 설정 할수 없습니다.");
    }

}
