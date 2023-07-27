package com.forum.postmanagementservice.exception;

public class InvalidStatusUpdateException extends RuntimeException {
    public InvalidStatusUpdateException(String message) {
        super(message);
    }
}
