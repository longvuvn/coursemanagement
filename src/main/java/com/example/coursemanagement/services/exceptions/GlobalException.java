package com.example.coursemanagement.services.exceptions;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.services.exceptions.errors.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIResponse<Object>> handleBadRequestException(BadRequestException e) {
        return buildErrorResponse("Bad Request",
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                "Yêu cầu không hợp lệ"
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<APIResponse<Object>> handleDuplicateResourceException(DuplicateResourceException e) {
        return buildErrorResponse("Conflict",
                e.getMessage(),
                HttpStatus.CONFLICT,
                "Tài nguyên bị trùng"
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<APIResponse<Object>> handleForbiddenException(ForbiddenException e) {
        return buildErrorResponse("Forbidden",
                e.getMessage(),
                HttpStatus.FORBIDDEN,
                "Truy cập bị từ chối"
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return buildErrorResponse("Not Found",
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                "Không tìm thấy dữ liệu"
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<APIResponse<Object>> handleUnauthorizedException(UnauthorizedException e) {
        return buildErrorResponse("Unauthorized",
                e.getMessage(),
                HttpStatus.UNAUTHORIZED,
                "Không có quyền truy cập"
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<Object>> handleAccessDeniedException(AccessDeniedException e) {
        return buildErrorResponse("Access Denied",
                e.getMessage(),
                HttpStatus.FORBIDDEN,
                "Bạn không có quyền thực hiện hành động này"
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<APIResponse<Object>> handleValidationException(ValidationException e) {
        return buildErrorResponse("Validation Failed",
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                "Dữ liệu không hợp lệ"
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        });

        APIResponse<Object> response = new APIResponse<>(
                "fail",
                "Entity validation failed",
                null,
                errors,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ResponseEntity<APIResponse<Object>> buildErrorResponse(String statusLabel, String message, HttpStatus status, String errorDescription) {
        APIResponse<Object> response = new APIResponse<>(
                "error",
                message,
                null,
                Map.of("error", errorDescription),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(response);
    }
}
