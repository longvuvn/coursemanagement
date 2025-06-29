package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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
    @NotBlank(message = "Tên không được để trống")
    private String fullName;


    @Email(message = "Email không hợp lệ")
    @Column(nullable = false, unique = true)
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|vn)$",
            message = "Email phải kết thúc bằng .com hoặc .vn"
    )
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 8, max = 255, message = "Mật khẩu phải có ít nhất 8 kí tự")
    private String password;

    @Size(min = 10, max = 10, message = "Số điện thoại phải có ít nhất 10 số")
    @NotBlank(message = "Số điện thoại không được để trống")
    @Column(nullable = false)
    private String phoneNumber;


    private UserStatus status;
    private String avatar;
    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
