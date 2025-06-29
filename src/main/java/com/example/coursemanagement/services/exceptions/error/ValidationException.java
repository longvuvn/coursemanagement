package com.example.coursemanagement.services.exceptions.error;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
