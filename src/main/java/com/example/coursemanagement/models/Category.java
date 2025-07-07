package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.CategoryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"category\"")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Không được để trống")
    private String name;

    private CategoryStatus status;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Course> course;
}
