package com.example.coursemanagement.repositories;


import com.example.coursemanagement.models.Learner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LearnerRepository extends JpaRepository<Learner, UUID> {
    boolean existsByEmail(String email);

    boolean existsByFullName(String fullName);

    boolean existsByPhoneNumber(String phoneNumber);
}
