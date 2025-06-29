package com.example.coursemanagement.models.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private String id;
    private String orderId;
    private String courseId;
    private String priceAtPurchase;
}
