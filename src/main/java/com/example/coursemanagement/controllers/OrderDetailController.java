package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.OrderDetailDTO;
import com.example.coursemanagement.services.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
@Tag(name = "OrderDetail Management", description = "Operations related to orders")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Operation(summary = "Get all order details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all order details")
    })
    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAll() {
        List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();
        return ResponseEntity.status(HttpStatus.OK).body(orderDetails);
    }

    @Operation(summary = "Get order detail by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order detail found"),
            @ApiResponse(responseCode = "404", description = "Order detail not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getById(@PathVariable String id) {
        OrderDetailDTO orderDetail = orderDetailService.getOrderDetailById(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderDetail);
    }

    @Operation(summary = "Create a new order detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order detail created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order detail input")
    })
    @PostMapping
    public ResponseEntity<OrderDetailDTO> create(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO created = orderDetailService.createOrderDetail(orderDetailDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing order detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order detail updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order detail not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> update(@PathVariable String id, @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO updated = orderDetailService.updateOrderDetail(orderDetailDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @Operation(summary = "Delete order detail by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order detail deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order detail not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}
