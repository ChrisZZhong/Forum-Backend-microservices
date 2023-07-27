package com.forum.postandreplyservice.exception;

public class InvalidReplyException extends RuntimeException {
    public InvalidReplyException(String message) {
        super(message);
    }
}
