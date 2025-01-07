package com.andrew.bookmark.exception.user;

public class NonExistentTokenException extends RuntimeException {
    public NonExistentTokenException(String message) {
        super(message);
    }
}
