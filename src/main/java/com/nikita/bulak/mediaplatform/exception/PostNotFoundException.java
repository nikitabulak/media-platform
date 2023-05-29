package com.nikita.bulak.mediaplatform.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
    }
}
