package com.room.manage.core.aop;

import com.room.manage.api.common.Response;
import com.room.manage.api.participation.model.dto.response.ExitResponseDto;
import com.room.manage.core.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Aspect
public class ExitLogging {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(com.room.manage.core.aop.annotation.ExitLog)")
    public Object ExitLog(ProceedingJoinPoint pjp) throws Throwable {
        ExitResponseDto exitResponseDto = null;
        Object result = pjp.proceed();

        if (!(result instanceof Exception)) {
            exitResponseDto = (ExitResponseDto) ((Response<?>) result).getData();
            logger.trace(LogUtil.exitLogStringFormat(exitResponseDto));
        }
        return result;
    }
}
