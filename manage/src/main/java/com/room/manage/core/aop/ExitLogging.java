package com.room.manage.core.aop;

import com.room.manage.api.user.exception.UserNotExistException;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.repository.UserRepository;
import com.room.manage.core.util.LogUtil;
import com.room.manage.core.util.SecurityUtil;
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
    private final UserRepository userRepository;

    @Around("@annotation(com.room.manage.core.aop.annotation.ExitLog)")
    public Object ExitLog(ProceedingJoinPoint pjp) throws Throwable {
        User user = userRepository.findById(SecurityUtil.getUserIdFromToken()).orElseThrow(UserNotExistException::new);
        Object result = pjp.proceed();
        logger.trace(LogUtil.exitLogStringFormat(user.getUserName()));
        return result;
    }
}
