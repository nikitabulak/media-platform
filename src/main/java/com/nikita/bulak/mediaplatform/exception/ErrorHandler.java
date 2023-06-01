package com.nikita.bulak.mediaplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler()
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleNotFoundException(final RuntimeException e) {
        e.printStackTrace();
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler({AuthorNotFoundException.class, FriendNotFoundException.class,
            FriendsRequestNotFoundException.class, PostNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundExceptions(final RuntimeException e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler({IllegalOperationException.class, WrongPostDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleWrongOrDamagedDtoExceptions(final RuntimeException e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
