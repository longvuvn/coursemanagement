package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Registration;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.models.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<Registration, UUID> {
    int countByLearnerId(UUID learnerId);

    @Query(value = "SELECT u.* FROM registration r JOIN user u ON r.learner_id = u.id WHERE r.course_id = UNHEX(REPLACE(:courseId, '-', ''))", nativeQuery = true)
    List<UserDTO> findUsersByCourseId(String courseId);

    @Query(value = "SELECT c.* FROM registration r JOIN course c ON r.course_id = c.id WHERE r.learner_id = UNHEX(REPLACE(:learner_id, '-', ''))", nativeQuery = true)
    List<CourseDTO> findCoursesByUserId(String learnerId);
}
