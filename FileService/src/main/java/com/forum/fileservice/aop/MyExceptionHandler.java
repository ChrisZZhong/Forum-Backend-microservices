package com.forum.fileservice.aop;

import com.amazonaws.SdkClientException;
import com.forum.fileservice.dto.response.ErrorResponse;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ErrorResponse.builder()
                        .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<ErrorResponse> handleIOException(Exception e){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ErrorResponse.builder()
                        .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                        .message(e.getMessage())
                        .build());
    }
    @ExceptionHandler(value = {SdkClientException.class})
    public ResponseEntity<ErrorResponse> handleSdkClientException(Exception e){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ErrorResponse.builder()
                        .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                        .message("S3Bucket Operation failed.")
                        .build());
    }
    @ExceptionHandler(value = {FileSizeLimitExceededException.class})
    public ResponseEntity<ErrorResponse> handleFileSizeLimitExceededException(Exception e){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ErrorResponse.builder()
                        .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                        .message("File size limit is exceeded.")
                        .build());
    }
}
