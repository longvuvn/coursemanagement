package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailDTO> getAllOrderDetails();
    OrderDetailDTO getOrderDetailById(String id);
    OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetailDTO updateOrderDetail(OrderDetailDTO orderDetailDTO, String id);
    void deleteOrderDetail(String id);
    List<OrderDetailDTO> getOrderDetailsByOrderId(String orderId);
}
