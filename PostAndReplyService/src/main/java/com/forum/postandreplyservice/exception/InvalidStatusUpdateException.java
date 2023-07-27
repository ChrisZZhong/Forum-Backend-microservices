package com.forum.postandreplyservice.exception;

public class InvalidStatusUpdateException extends RuntimeException {
    public InvalidStatusUpdateException(String message) {
        super(message);
    }
}
