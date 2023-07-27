package com.forum.postmanagementservice.exception;

public class InvalidPostStatusException extends RuntimeException {
    public InvalidPostStatusException(String message) {
        super(message);
    }
}
