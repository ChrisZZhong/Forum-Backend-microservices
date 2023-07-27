package com.forum.postmanagementservice.exception;

public class InvalidReplyException extends RuntimeException {
    public InvalidReplyException(String message) {
        super(message);
    }
}
