package com.forum.postandreplyservice.exception;

public class InvalidPostStatusException extends RuntimeException {
    public InvalidPostStatusException(String message) {
        super(message);
    }
}
