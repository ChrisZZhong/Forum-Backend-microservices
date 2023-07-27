package com.forum.postmanagementservice.aop;

import com.forum.postmanagementservice.dto.ServiceStatus;
import com.forum.postmanagementservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ServiceStatus> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ServiceStatus.builder()
                        .success(false)
                        .message("ExceptionHandlingCenter: Unexpected error.")
                        .build());
    }

    @ExceptionHandler(value = {PostNotFoundException.class})
    public ResponseEntity<ServiceStatus> handlePostNotFoundException(PostNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ServiceStatus.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = {ReplyNotFoundException.class})
    public ResponseEntity<ServiceStatus> handleReplyNotFoundException(ReplyNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ServiceStatus.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = {UnauthorizedPostAccessException.class})
    public ResponseEntity<ServiceStatus> handleUnauthorizedPostAccessException(UnauthorizedPostAccessException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ServiceStatus.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = {UnauthorizedPostStatusUpdateException.class})
    public ResponseEntity<ServiceStatus> handleUnauthorizedPostStatusUpdateException(UnauthorizedPostStatusUpdateException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ServiceStatus.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = {InvalidStatusUpdateException.class})
    public ResponseEntity<ServiceStatus> handleInvalidStatusUpdateException(InvalidStatusUpdateException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ServiceStatus.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = {InvalidReplyException.class})
    public ResponseEntity<ServiceStatus> handleInvalidReplyException(InvalidReplyException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ServiceStatus.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = {InvalidPostStatusException.class})
    public ResponseEntity<ServiceStatus> handleInvalidPostStatusException(InvalidPostStatusException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ServiceStatus.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build());
    }
}
