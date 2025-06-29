package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.Order;
import com.example.coursemanagement.models.OrderDetail;
import com.example.coursemanagement.models.dto.OrderDetailDTO;
import com.example.coursemanagement.repositories.CourseRepository;
import com.example.coursemanagement.repositories.OrderDetailRepository;
import com.example.coursemanagement.repositories.OrderRepository;
import com.example.coursemanagement.services.CourseService;
import com.example.coursemanagement.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        return orderDetails.stream()
                .map(this::orderDetailToOrderDetailDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailDTO getOrderDetailById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(uuid);
        return orderDetail.map(this::orderDetailToOrderDetailDTO).orElse(null);
    }

    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        UUID orderUUID = UUID.fromString(orderDetailDTO.getOrderId());
        UUID courseId = UUID.fromString(orderDetailDTO.getCourseId());


        Order order = orderRepository.findById(orderUUID).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setCourse(course);
        orderDetail.setPriceAtPurchase(BigDecimal.valueOf(Double.parseDouble(orderDetailDTO.getPriceAtPurchase())));
        return orderDetailToOrderDetailDTO(orderDetailRepository.save(orderDetail));
    }

    @Override
    public OrderDetailDTO updateOrderDetail(OrderDetailDTO orderDetailDTO, String id) {
        UUID uuid = UUID.fromString(id);
        OrderDetail existingOrderDetail = orderDetailRepository.findById(uuid).orElse(null);
        existingOrderDetail.setPriceAtPurchase(BigDecimal.valueOf(Double.parseDouble(orderDetailDTO.getPriceAtPurchase())));
        return orderDetailToOrderDetailDTO(orderDetailRepository.save(existingOrderDetail));
    }

    @Override
    public void deleteOrderDetail(String id) {
        UUID uuid = UUID.fromString(id);
        OrderDetail existingOrderDetail = orderDetailRepository.findById(uuid).orElse(null);
        orderDetailRepository.delete(existingOrderDetail);
    }

    @Override
    public List<OrderDetailDTO> getOrderDetailsByOrderId(String orderId) {
        UUID uuid = UUID.fromString(orderId);
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailsByOrderId(uuid);
        return orderDetails.stream()
                .map(this::orderDetailToOrderDetailDTO)
                .collect(Collectors.toList());
    }


    public OrderDetailDTO orderDetailToOrderDetailDTO(OrderDetail orderDetail){
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(String.valueOf(orderDetail.getId()));
        dto.setCourseId(String.valueOf(orderDetail.getCourse().getId()));
        dto.setOrderId(String.valueOf(orderDetail.getOrder().getId()));
        dto.setPriceAtPurchase(String.valueOf(orderDetail.getPriceAtPurchase()));
        return dto;
    }
}
