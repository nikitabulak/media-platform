package com.nikita.bulak.mediaplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final RuntimeException e) {
        e.printStackTrace();
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalOperationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotAvailableException(final RuntimeException e) {
        e.printStackTrace();
        return new ErrorResponse(
                e.getMessage()
        );
    }
}
