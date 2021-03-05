package com.room.manage.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class Response<T> {
    int status;
    T data;

    public Response(HttpStatus httpStatus, T data) {
        this.status = httpStatus.value();
        this.data = data;
    }
}
