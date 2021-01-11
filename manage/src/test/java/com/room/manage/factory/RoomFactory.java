package com.room.manage.factory;

import org.springframework.stereotype.Component;

@Component
public class RoomFactory {
    private String floor = "3";
    private String field = "B";
    private int maxNum = 10;


    public String getFloor() {
        return floor;
    }

    public String getField() {
        return field;
    }

    public int getMaxNum() {
        return maxNum;
    }
}
