package com.example.coursemanagement.services;

import com.example.coursemanagement.enums.OrderStatus;
import com.example.coursemanagement.models.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(String id);
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(String id, OrderStatus status);
    void deleteOrder(String id);
}
