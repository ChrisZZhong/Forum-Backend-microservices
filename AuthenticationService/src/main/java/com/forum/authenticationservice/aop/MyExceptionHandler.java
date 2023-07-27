package com.forum.authenticationservice.aop;

import com.forum.authenticationservice.dto.response.ErrorResponse;
import com.forum.authenticationservice.exception.AlreadyVerifiedException;
import com.forum.authenticationservice.exception.ErrorTokenException;
import com.forum.authenticationservice.exception.SignUpFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(ErrorResponse.builder().status("failed").message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleBadLoginRequestException(BadCredentialsException e){
        e.printStackTrace();
        return new ResponseEntity<>(ErrorResponse.builder().status("failed").message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e){
        e.printStackTrace();
        return new ResponseEntity<>(ErrorResponse.builder().status("failed").message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {SignUpFailedException.class})
    public ResponseEntity<ErrorResponse> handleSignUpFailedException(SignUpFailedException e){
        e.printStackTrace();
        return new ResponseEntity<>(ErrorResponse.builder().status("failed").message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {ErrorTokenException.class})
    public ResponseEntity<ErrorResponse> handleErrorTokenException(ErrorTokenException e){
        e.printStackTrace();
        return new ResponseEntity<>(ErrorResponse.builder().status("failed").message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {AlreadyVerifiedException.class})
    public ResponseEntity<ErrorResponse> handleAlreadyVerifiedException(AlreadyVerifiedException e){
        e.printStackTrace();
        return new ResponseEntity<>(ErrorResponse.builder().status("failed").message(e.getMessage()).build(), HttpStatus.OK);
    }
}
