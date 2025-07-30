package com.example.coursemanagement.services.exceptions;

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
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), "Yêu cầu không hợp lệ");
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateResourceException(DuplicateResourceException e) {
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage(), "Tài nguyên bị trùng");
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbiddenException(ForbiddenException e) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, e.getMessage(), "Truy cập bị từ chối");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), "Không tìm thấy dữ liệu");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException e) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), "Không có quyền truy cập");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException e) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, e.getMessage(), "Bạn không có quyền thực hiện hành động này");
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), "Dữ liệu không hợp lệ");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "Validation failed");
        response.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Exception mặc định cho các lỗi chưa xử lý
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "Lỗi hệ thống");
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message, String errorDescription) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("timestamp", LocalDateTime.now());
        response.put("message", message);
        response.put("error", errorDescription);
        return ResponseEntity.status(status).body(response);
    }
}
