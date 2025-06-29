package com.example.coursemanagement.services.exceptions.error;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super(message);
    }
}
