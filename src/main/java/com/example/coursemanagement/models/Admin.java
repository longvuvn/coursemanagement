package com.example.coursemanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "\"admin\"")
@Data
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {
    @Column(nullable = false, length = 100)
    private String department;
}
