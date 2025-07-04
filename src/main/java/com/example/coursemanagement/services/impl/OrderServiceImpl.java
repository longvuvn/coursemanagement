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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.Instant;
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
        return optionalOrder.map(order -> modelMapper.map(order,OrderDTO.class)).orElse(null);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Instant now = Instant.now();
        UUID learnerId = UUID.fromString(orderDTO.getLearnerId());
        Learner learner = learnerRepository.findById(learnerId).orElse(null);

        Order order = modelMapper.map(orderDTO, Order.class);
        order.setStatus(OrderStatus.ACTIVE);
        order.setCreatedAt(now);
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
    public OrderDTO updateOrder(OrderDTO orderDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Order existingOrder = orderRepository.findById(uuid).orElse(null);
        existingOrder.setStatus(OrderStatus.valueOf(orderDTO.getStatus()));
        return modelMapper.map(orderRepository.save(existingOrder), OrderDTO.class);
    }

    @Override
    public void deleteOrder(String id) {
        UUID uuid = UUID.fromString(id);
        Order existingOrder = orderRepository.findById(uuid).orElse(null);
        orderRepository.delete(existingOrder);
    }
}
