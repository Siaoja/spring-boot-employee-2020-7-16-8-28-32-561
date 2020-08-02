package com.thoughtworks.springbootemployee.handler;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(NoSuchDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handlerNoSuchDataException() {

    }

    @ExceptionHandler(IllegalOperationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    void handlerIllegalOperationException() {

    }
}
