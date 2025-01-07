package com.andrew.bookmark.exception.url;

public class ForbiddenUpdateException extends RuntimeException {
    public ForbiddenUpdateException(String message) {
        super(message);
    }
}
