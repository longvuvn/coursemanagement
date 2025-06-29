package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"order\"")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private OrderStatus status;

    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Learner learner;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;
}