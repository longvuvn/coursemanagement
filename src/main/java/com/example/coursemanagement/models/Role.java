package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.RoleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"role\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Tên không được để trống")
    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String name;

    private RoleStatus status;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users;
}
