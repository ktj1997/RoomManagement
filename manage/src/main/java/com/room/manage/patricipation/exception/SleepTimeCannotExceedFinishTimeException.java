package com.room.manage.patricipation.exception;

import com.room.manage.patricipation.model.entity.Sleep;

public class SleepTimeCannotExceedFinishTimeException extends RuntimeException{
    public SleepTimeCannotExceedFinishTimeException()
    {
        super("종료시간을 넘어서 부재시간을 설정 할수 없습니다.");
    }

}
