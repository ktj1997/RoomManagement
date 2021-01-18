package com.room.manage.notice;

import lombok.Getter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Notice {
    ConcurrentHashMap<Long, SseEmitter> userMap = new ConcurrentHashMap();

}
