package com.example.coursemanagement.services.exceptions.error;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
