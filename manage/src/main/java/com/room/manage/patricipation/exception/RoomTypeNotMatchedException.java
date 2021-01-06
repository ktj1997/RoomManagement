package com.room.manage.patricipation.exception;


public class RoomTypeNotMatchedException extends RuntimeException{
    public RoomTypeNotMatchedException()
    {
        super("Room의 Type이 일치하지 않습니다.");
    }
}
