package com.forum.postandreplyservice.exception;

public class UnauthorizedPostStatusUpdateException extends RuntimeException {
    public UnauthorizedPostStatusUpdateException(String message) {
        super(message);
    }
}
