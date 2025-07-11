package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.OrderStatus;
import com.example.coursemanagement.models.Learner;
import com.example.coursemanagement.models.Order;
import com.example.coursemanagement.models.dto.OrderDTO;
import com.example.coursemanagement.models.dto.OrderDetailDTO;
import com.example.coursemanagement.repositories.LearnerRepository;
import com.example.coursemanagement.repositories.OrderRepository;
import com.example.coursemanagement.services.OrderDetailService;
import com.example.coursemanagement.services.OrderService;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final LearnerRepository learnerRepository;
    private final OrderDetailService orderDetailService;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();
    }

    @Override
    public OrderDTO getOrderById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Order> optionalOrder = orderRepository.findById(uuid);
        return optionalOrder.map(order -> modelMapper.map(order,OrderDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Order"));
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        UUID learnerId = UUID.fromString(orderDTO.getLearnerId());
        Learner learner = learnerRepository.findById(learnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Learner"));
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setStatus(OrderStatus.PENDING);
        order.setLearner(learner);
        Order savedOrder = orderRepository.save(order);
        if (savedOrder.getId() != null) {
            OrderDetailDTO detailDTO = orderDTO.getOrderDetail();
            detailDTO.setOrderId(savedOrder.getId().toString());
            orderDetailService.createOrderDetail(detailDTO);
        }

        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO updateOrder(String id, OrderStatus status) {
        UUID uuid = UUID.fromString(id);
        Order existingOrder = orderRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Order"));
        existingOrder.setStatus(status);
        Order updated = orderRepository.save(existingOrder);
        return modelMapper.map(updated, OrderDTO.class);
    }

    @Override
    public void deleteOrder(String id) {
        UUID uuid = UUID.fromString(id);
        Order existingOrder = orderRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Order"));
        orderRepository.delete(existingOrder);
    }
}