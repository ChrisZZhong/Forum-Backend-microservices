package com.forum.postmanagementservice.exception;

public class UnauthorizedPostStatusUpdateException extends RuntimeException {
    public UnauthorizedPostStatusUpdateException(String message) {
        super(message);
    }
}
