package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.OrderDetailDTO;
import com.example.coursemanagement.services.OrderDetailService;
import com.example.coursemanagement.services.exceptions.errors.BadRequestException;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    //lấy tất cả orderdetail
    @GetMapping
    public ResponseEntity<APIResponse<List<OrderDetailDTO>>> getAll() {
        try{
            List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();
            APIResponse<List<OrderDetailDTO>> response = new APIResponse<>(
                    "success",
                    "Order details retrieved successfully",
                    orderDetails,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<OrderDetailDTO>> response = new APIResponse<>(
                    "error",
                    "Order details retrieved failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //lấy orderdetail theo orderid
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<OrderDetailDTO>> getById(@Valid @PathVariable String id) {
        try{
            OrderDetailDTO orderDetail = orderDetailService.getOrderDetailById(id);
            APIResponse<OrderDetailDTO> response = new APIResponse<>(
                    "success",
                    "Order detail retrieved successfully",
                    orderDetail,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<OrderDetailDTO> response = new APIResponse<>(
                    "error",
                    "Order not found",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //tạo orderdetail
    @PostMapping
    public ResponseEntity<APIResponse<OrderDetailDTO>> create(@RequestBody OrderDetailDTO orderDetailDTO) {
        try{
            OrderDetailDTO created = orderDetailService.createOrderDetail(orderDetailDTO);
            APIResponse<OrderDetailDTO> response = new APIResponse<>(
                    "success",
                    "Order detail created successfully",
                    created,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (BadRequestException ex){
            APIResponse<OrderDetailDTO> response = new APIResponse<>(
                    "error",
                    "Order detail created failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //chỉnh sửa orderdetail
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<OrderDetailDTO>> update(@PathVariable String id, @RequestBody OrderDetailDTO orderDetailDTO) {
        try{
            OrderDetailDTO updated = orderDetailService.updateOrderDetail(orderDetailDTO, id);
            APIResponse<OrderDetailDTO> response = new APIResponse<>(
                    "success",
                    "Order detail updated successfully",
                    updated,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<OrderDetailDTO> response = new APIResponse<>(
                    "error",
                    "Order detail updated failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // xóa orderdetail
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try{
            orderDetailService.deleteOrderDetail(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Order detail deleted successfully",
                    null,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Order detail deleted failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
