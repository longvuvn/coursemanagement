package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
