package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String fullName;


    @Email(message = "Email không hợp lệ")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Size(min = 8, max = 255, message = "Mật khẩu phải có ít nhất 8 kí tự")

    private String password;

    @Size(min = 10, max = 10)
    private String phoneNumber;


    private UserStatus status;
    private String avatar;
    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


}
