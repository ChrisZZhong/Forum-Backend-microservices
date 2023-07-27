package com.forum.authenticationservice.exception;

public class BannedUserException extends Exception{
    BannedUserException(String message){
        super(message);
    }
}
