package com.example.coursemanagement.models.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private String id;
    private String status;
    private String createdAt;
    private String learnerId;
    private OrderDetailDTO orderDetail;
}
