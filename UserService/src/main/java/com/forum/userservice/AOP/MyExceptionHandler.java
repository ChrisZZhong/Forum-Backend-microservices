package com.forum.userservice.AOP;

import com.forum.userservice.dto.response.ErrorResponse;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = {InvalidCredentialsException.class})
    public ResponseEntity<ErrorResponse> productNotFoundException(InvalidCredentialsException e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message(e.getMessage()).build());
    }
    
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder().message("Provided credential is invalid, login failed").build());
    }
    
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().message(e.getMessage()).build());
    }
}
