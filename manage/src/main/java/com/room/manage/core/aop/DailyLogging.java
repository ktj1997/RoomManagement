package com.room.manage.core.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Aspect
public class DailyLogging {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("@annotation(com.room.manage.core.aop.annotation.DailyLog)")
    public void DailyLog() {
        logger.trace("new Day");
    }
}
