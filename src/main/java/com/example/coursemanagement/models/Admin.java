package com.example.coursemanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "\"admin\"")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class Admin extends User {

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Không được để trống")
    private String department;
}
