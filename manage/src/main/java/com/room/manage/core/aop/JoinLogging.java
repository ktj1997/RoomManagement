package com.room.manage.core.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room.manage.api.participation.model.dto.request.ParticipationRequestDto;
import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.api.user.exception.UserNotExistException;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.repository.UserRepository;
import com.room.manage.core.util.LogUtil;
import com.room.manage.core.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Executable;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class JoinLogging {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    @Around("@annotation(com.room.manage.core.aop.annotation.JoinLog)")
    public Object JoinLog(ProceedingJoinPoint pjp) throws Throwable {
        User user = userRepository.findById(SecurityUtil.getUserIdFromToken()).orElseThrow(UserNotExistException::new);
        ParticipationRequestDto participationRequestDto = null;
        Object result = pjp.proceed();

        if (!(result instanceof Exception)) {
            for (Object obj : pjp.getArgs()) {
                if (obj instanceof ParticipationRequestDto) {
                    participationRequestDto = (ParticipationRequestDto) obj;
                    logger.info(LogUtil.joinLogStringFormat(user.getUserName(), participationRequestDto));
                    break;
                }
            }
        }
        return result;
    }
}
