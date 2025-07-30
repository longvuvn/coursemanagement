package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.OrderDetailDTO;
import com.example.coursemanagement.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    // Lấy tất cả OrderDetail
    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAll() {
        List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();
        return ResponseEntity.status(HttpStatus.OK).body(orderDetails);
    }

    // Lấy OrderDetail theo ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getById(@PathVariable String id) {
        OrderDetailDTO orderDetail = orderDetailService.getOrderDetailById(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderDetail);
    }

    // Tạo mới OrderDetail
    @PostMapping
    public ResponseEntity<OrderDetailDTO> create(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO created = orderDetailService.createOrderDetail(orderDetailDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Cập nhật OrderDetail
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> update(@PathVariable String id, @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO updated = orderDetailService.updateOrderDetail(orderDetailDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // Xóa OrderDetail
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}

