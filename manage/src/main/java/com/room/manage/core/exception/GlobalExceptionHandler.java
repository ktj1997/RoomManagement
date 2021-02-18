package com.room.manage.core.exception;


import com.room.manage.api.auth.exception.DuplicateUserNameException;
import com.room.manage.api.auth.exception.WrongLoginInfoException;
import com.room.manage.api.participation.exception.*;
import com.room.manage.api.participation.exception.AlreadyMaximumParticipantException;
import com.room.manage.api.room.exception.RoomNotExistException;
import com.room.manage.api.user.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value ={DuplicateUserNameException.class, WrongLoginInfoException.class} )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse AuthExceptionHandler(Exception e)
    {
        ExceptionCode exceptionCode = null;
        if(e instanceof DuplicateUserNameException)
            exceptionCode = ExceptionCode.DUPLICATE_USERNAME;
        else
            exceptionCode = ExceptionCode.WRONG_LOGIN_INFO;

        return new ExceptionResponse(HttpStatus.BAD_REQUEST,exceptionCode);
    }

    @ExceptionHandler(value =
            {InvalidTimeRequestException.class, NoParticipationException.class,
                    AlreadyMaximumParticipantException.class,AlreadySleepStatusException.class, AlreadyParticipateException.class, RemainSleepNumZeroException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse ParticipationExceptionHandler(Exception e)
    {
        ExceptionCode exceptionCode = null;

        if(e instanceof InvalidTimeRequestException)
           exceptionCode = ExceptionCode.INVALID_TIME_REQUEST;
        else if(e instanceof NoParticipationException)
            exceptionCode = ExceptionCode.NO_PARTICIPATION;
        else if(e instanceof AlreadyParticipateException)
            exceptionCode = ExceptionCode.ALREADY_PARTICIPATION;
        else if(e instanceof RemainSleepNumZeroException)
            exceptionCode = ExceptionCode.SLEEP_NUM_ZERO;
        else if(e instanceof AlreadySleepStatusException)
            exceptionCode = ExceptionCode.ALREADY_PARTICIPATION;
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,exceptionCode);
    }
    @ExceptionHandler({AlarmExecutionException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse SocketConnectionExceptionHandler(Exception e)
    {
        ExceptionCode exceptionCode = null;
        if(e instanceof AlarmExecutionException)
            exceptionCode = ExceptionCode.CONNECTION_CLOSED;
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,exceptionCode);
    }

    @ExceptionHandler(RoomNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse RoomExceptionHandler(Exception e){
        ExceptionCode exceptionCode = null;

        if(e instanceof RoomNotExistException)
            exceptionCode = ExceptionCode.ROOM_NOT_EXIST;
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,exceptionCode);
    }

    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse UserExceptionHandler(Exception e){
        ExceptionCode exceptionCode = null;
        if(e instanceof UserNotExistException)
            exceptionCode = ExceptionCode.USER_NOT_EXIST;

        return new ExceptionResponse(HttpStatus.BAD_REQUEST,exceptionCode);
    }

}
