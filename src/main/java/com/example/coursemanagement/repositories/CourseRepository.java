package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}
