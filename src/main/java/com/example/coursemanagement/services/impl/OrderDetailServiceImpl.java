package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.Order;
import com.example.coursemanagement.models.OrderDetail;
import com.example.coursemanagement.models.dto.OrderDetailDTO;
import com.example.coursemanagement.repositories.CourseRepository;
import com.example.coursemanagement.repositories.OrderDetailRepository;
import com.example.coursemanagement.repositories.OrderRepository;
import com.example.coursemanagement.services.OrderDetailService;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        return orderDetails.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailDTO getOrderDetailById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(uuid);
        return optionalOrderDetail.map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class)).orElse(null);
    }

    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        UUID orderUUID = UUID.fromString(orderDetailDTO.getOrderId());
        UUID courseUUID = UUID.fromString(orderDetailDTO.getCourseId());

        Order order = orderRepository.findById(orderUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Order"));
        Course course = courseRepository.findById(courseUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Course"));

        OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
        orderDetail.setOrder(order);
        orderDetail.setCourse(course);
        orderDetail.setPriceAtPurchase(BigDecimal.valueOf(Double.parseDouble(orderDetailDTO.getPriceAtPurchase())));
        return modelMapper.map(orderDetailRepository.save(orderDetail), OrderDetailDTO.class);
    }

    @Override
    public OrderDetailDTO updateOrderDetail(OrderDetailDTO orderDetailDTO, String id) {
        UUID uuid = UUID.fromString(id);
        OrderDetail existingOrderDetail = orderDetailRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This OrderDetail"));
        existingOrderDetail
                .setPriceAtPurchase(BigDecimal.valueOf(Double.parseDouble(orderDetailDTO.getPriceAtPurchase())));
        return modelMapper.map(orderDetailRepository.save(existingOrderDetail), OrderDetailDTO.class);
    }

    @Override
    public void deleteOrderDetail(String id) {
        UUID uuid = UUID.fromString(id);
        OrderDetail existingOrderDetail = orderDetailRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This OrderDetail"));
        orderDetailRepository.delete(existingOrderDetail);
    }

    @Override
    public List<OrderDetailDTO> getOrderDetailsByOrderId(String orderId) {
        UUID uuid = UUID.fromString(orderId);
        List<OrderDetail> orderDetailList = orderDetailRepository.getOrderDetailsByOrderId(uuid);
        return orderDetailList.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class) )
                .collect(Collectors.toList());
    }
}
