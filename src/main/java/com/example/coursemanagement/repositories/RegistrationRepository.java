package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<Registration, UUID> {
    int countByLearnerId(UUID learnerId);
}
