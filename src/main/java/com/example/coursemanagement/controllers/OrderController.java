package com.example.coursemanagement.controllers;

import com.example.coursemanagement.enums.OrderStatus;
import com.example.coursemanagement.models.dto.OrderDTO;
import com.example.coursemanagement.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Lấy tất cả order
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAll() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    // Lấy order theo id
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable String id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    // Tạo order
    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO created = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Cập nhật trạng thái order
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> update(@PathVariable String id, @RequestParam OrderStatus status) {
        OrderDTO updated = orderService.updateOrder(id, status);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // Xóa order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}

