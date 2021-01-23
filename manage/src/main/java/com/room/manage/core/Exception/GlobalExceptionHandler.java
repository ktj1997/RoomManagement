package com.room.manage.core.Exception;


import com.room.manage.api.auth.exception.DuplicateUserNameException;
import com.room.manage.api.auth.exception.WrongLoginInfoException;
import com.room.manage.api.patricipation.exception.*;
import com.room.manage.api.room.exception.AlreadyMaximumParticipantException;
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
        String code = null;
        if(e instanceof DuplicateUserNameException)
            code = "AUTH=001";
        else
            code = "AUTH=002";

        return new ExceptionResponse(e,HttpStatus.BAD_REQUEST,code);
    }

    @ExceptionHandler(value =
            {InvalidTimeRequestException.class, NoSuchParticipationException.class,
                    AlreadySleepStatusException.class, AlreadyParticipateException.class, SleepRequestDenyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse ParticipationExceptionHandler(Exception e)
    {
        String code = null;

        if(e instanceof InvalidTimeRequestException)
            code ="PARTICIPATION-001";
        else if(e instanceof NoSuchParticipationException)
            code ="PARTICIPATION-002";
        else if(e instanceof AlreadyParticipateException)
            code ="PARTICIPATION-003";
        else if(e instanceof SleepRequestDenyException)
            code ="PARTICIPATION-004";
        else if(e instanceof AlreadySleepStatusException)
            code ="PARTICIPATION-005";
        return new ExceptionResponse(e,HttpStatus.BAD_REQUEST,code);
    }
    @ExceptionHandler({ConnectionClosedException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse SocketConnectionExceptionHandler(Exception e)
    {
        String code =null;
        if(e instanceof ConnectionClosedException)
            code = "PARTICIPATION-006";
        return new ExceptionResponse(e,HttpStatus.INTERNAL_SERVER_ERROR,code);
    }

    @ExceptionHandler({RoomNotExistException.class, AlreadyMaximumParticipantException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse RoomExceptionHandler(Exception e){
        String code = null;

        if(e instanceof RoomNotExistException)
            code = "ROOM-001";
        else
            code = "ROOM-002";
        return new ExceptionResponse(e,HttpStatus.BAD_REQUEST,code);
    }

    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse UserExceptionHandler(Exception e){
        String code = null;

        if(e instanceof UserNotExistException)
            code = "USER-001";
        return new ExceptionResponse(e,HttpStatus.BAD_REQUEST,code);
    }

}
