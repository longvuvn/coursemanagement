package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.Learner;
import com.example.coursemanagement.models.Registration;
import com.example.coursemanagement.models.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<Registration, UUID> {
    int countByLearnerId(UUID learnerId);
}
