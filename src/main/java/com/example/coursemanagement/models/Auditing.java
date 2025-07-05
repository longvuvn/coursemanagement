package com.example.coursemanagement.models;

 v10
import jakarta.persistence.EntityListeners;

 main
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;



import java.time.Instant;

@MappedSuperclass

@EntityListeners(AuditingEntityListener.class)

@Data
public class Auditing {
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

