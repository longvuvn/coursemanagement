package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.OrderDTO;
import com.example.coursemanagement.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //lấy tất cả order
    @GetMapping
    public ResponseEntity<APIResponse<List<OrderDTO>>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        APIResponse<List<OrderDTO>> response = new APIResponse<>(
                "success",
                "Orders retrieved successfully",
                orders,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //lấy order theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<OrderDTO>> getOrderById(@PathVariable String id) {
        OrderDTO order = orderService.getOrderById(id);
        APIResponse<OrderDTO> response = new APIResponse<>(
                "success",
                "Order retrieved successfully",
                order,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //tạo order
    @PostMapping
    public ResponseEntity<APIResponse<OrderDTO>> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO created = orderService.createOrder(orderDTO);
        APIResponse<OrderDTO> response = new APIResponse<>(
                "success",
                "Order created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //chỉnh sửa order
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<OrderDTO>> updateOrder(@PathVariable String id, @RequestBody OrderDTO orderDTO) {
        OrderDTO updated = orderService.updateOrder(orderDTO, id);
        APIResponse<OrderDTO> response = new APIResponse<>(
                "success",
                "Order updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //xóa order
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Order deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
