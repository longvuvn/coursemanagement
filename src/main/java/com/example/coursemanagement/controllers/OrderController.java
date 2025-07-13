package com.example.coursemanagement.controllers;

import com.example.coursemanagement.enums.OrderStatus;
import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.OrderDTO;
import com.example.coursemanagement.services.OrderService;
import com.example.coursemanagement.services.exceptions.errors.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Lấy tất cả order
    @GetMapping
    public ResponseEntity<APIResponse<List<OrderDTO>>> getAll() {
        try {
            List<OrderDTO> orders = orderService.getAllOrders();
            APIResponse<List<OrderDTO>> response = new APIResponse<>(
                    "success",
                    "Orders retrieved successfully",
                    orders,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            APIResponse<List<OrderDTO>> response = new APIResponse<>(
                    "error",
                    "Failed to retrieve orders",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Lấy order theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<OrderDTO>> getById(@PathVariable String id) {
        try {
            OrderDTO order = orderService.getOrderById(id);
            APIResponse<OrderDTO> response = new APIResponse<>(
                    "success",
                    "Order retrieved successfully",
                    order,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            APIResponse<OrderDTO> response = new APIResponse<>(
                    "error",
                    "Failed to retrieve order",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Tạo order
    @PostMapping
    public ResponseEntity<APIResponse<OrderDTO>> create(@Valid @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO created = orderService.createOrder(orderDTO);
            APIResponse<OrderDTO> response = new APIResponse<>(
                    "success",
                    "Order created successfully",
                    created,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            APIResponse<OrderDTO> response = new APIResponse<>(
                    "error",
                    "Failed to create order",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Cập nhật trạng thái order
    @PutMapping("/{id}/status")
    public ResponseEntity<APIResponse<OrderDTO>> update(@PathVariable String id, @RequestParam OrderStatus status) {
        try {
            OrderDTO updated = orderService.updateOrder(id, status);
            APIResponse<OrderDTO> response = new APIResponse<>(
                    "success",
                    "Order updated successfully",
                    updated,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            APIResponse<OrderDTO> response = new APIResponse<>(
                    "error",
                    "Failed to update order",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Xóa order
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try {
            orderService.deleteOrder(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Order deleted successfully",
                    null,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception ex) {
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Failed to delete order",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
