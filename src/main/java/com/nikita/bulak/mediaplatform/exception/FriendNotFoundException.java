package com.nikita.bulak.mediaplatform.exception;

public class FriendNotFoundException extends RuntimeException{
    public FriendNotFoundException(String message) {
        super(message);
    }
}
