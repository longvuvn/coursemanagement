package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.OrderDetailDTO;
import com.example.coursemanagement.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order_details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    //lấy tất cả orderdetail
    @GetMapping
    public ResponseEntity<APIResponse<List<OrderDetailDTO>>> getAllOrderDetails() {
        List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();
        APIResponse<List<OrderDetailDTO>> response = new APIResponse<>(
                "success",
                "Order details retrieved successfully",
                orderDetails,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //lấy orderdetail theo orderid
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<OrderDetailDTO>> getOrderDetailById(@Valid @PathVariable String id) {
        OrderDetailDTO orderDetail = orderDetailService.getOrderDetailById(id);
        APIResponse<OrderDetailDTO> response = new APIResponse<>(
                "success",
                "Order detail retrieved successfully",
                orderDetail,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //tạo orderdetail
    @PostMapping
    public ResponseEntity<APIResponse<OrderDetailDTO>> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO created = orderDetailService.createOrderDetail(orderDetailDTO);
        APIResponse<OrderDetailDTO> response = new APIResponse<>(
                "success",
                "Order detail created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //chỉnh sửa orderdetail
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<OrderDetailDTO>> updateOrderDetail(@PathVariable String id, @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO updated = orderDetailService.updateOrderDetail(orderDetailDTO, id);
        APIResponse<OrderDetailDTO> response = new APIResponse<>(
                "success",
                "Order detail updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    // xóa orderdetail
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteOrderDetail(@PathVariable String id) {
        orderDetailService.deleteOrderDetail(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Order detail deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
