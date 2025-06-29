package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.User;
import com.example.coursemanagement.models.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
