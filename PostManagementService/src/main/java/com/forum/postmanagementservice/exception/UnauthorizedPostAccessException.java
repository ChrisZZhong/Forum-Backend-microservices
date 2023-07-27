package com.forum.postmanagementservice.exception;

public class UnauthorizedPostAccessException extends RuntimeException {
    public UnauthorizedPostAccessException(String message) {
        super(message);
    }
}
