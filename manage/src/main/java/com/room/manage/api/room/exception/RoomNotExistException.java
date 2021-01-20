package com.room.manage.api.room.exception;

public class RoomNotExistException extends RuntimeException{
    public RoomNotExistException(){
        super("Room이 존재하지 않습니다.");
    }
}
