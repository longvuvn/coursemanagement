package com.example.coursemanagement.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
    private String status;
    private String message;
    private T data;
    private Map<String, String> errorCode;
    private LocalDateTime timestamp = LocalDateTime.now();
}
