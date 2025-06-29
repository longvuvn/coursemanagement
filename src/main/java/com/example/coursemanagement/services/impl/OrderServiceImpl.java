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

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::orderToOrderDTO)
                .toList();
    }

    @Override
    public OrderDTO getOrderById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Order> order = orderRepository.findById(uuid);
        return order.map(this::orderToOrderDTO).orElse(null);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Instant now = Instant.now();
        UUID learnerId = UUID.fromString(orderDTO.getLearnerId());
        Learner learner = learnerRepository.findById(learnerId).orElse(null);

        Order order = new Order();
        order.setStatus(OrderStatus.ACTIVE);
        order.setCreatedAt(now);
        order.setLearner(learner);
        Order savedOrder = orderRepository.save(order);
        if (savedOrder.getId() != null) {
            OrderDetailDTO detailDTO = orderDTO.getOrderDetail();
            detailDTO.setOrderId(savedOrder.getId().toString());
            orderDetailService.createOrderDetail(detailDTO);
        }

        return orderToOrderDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Order existingOrder = orderRepository.findById(uuid).orElse(null);
        existingOrder.setStatus(OrderStatus.valueOf(orderDTO.getStatus()));
        return orderToOrderDTO(orderRepository.save(existingOrder));
    }

    @Override
    public void deleteOrder(String id) {
        UUID uuid = UUID.fromString(id);
        Order existingOrder = orderRepository.findById(uuid).orElse(null);
        orderRepository.delete(existingOrder);
    }

    public OrderDTO orderToOrderDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(String.valueOf(order.getId()));
        dto.setCreatedAt(String.valueOf(order.getCreatedAt()));
        dto.setStatus(order.getStatus().name());
        List<OrderDetailDTO> detailDTO = orderDetailService.getOrderDetailsByOrderId(order.getId().toString());
        if (detailDTO != null && !detailDTO.isEmpty()) {
            dto.setOrderDetail(detailDTO.get(0));
        } else {
            dto.setOrderDetail(null);
        }
        dto.setLearnerId(String.valueOf(order.getLearner().getId()));
        return dto;
    }
}
