package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearnerRepository extends JpaRepository<Learner, UUID> {
    boolean existsByEmail(String email);

    boolean existsByFullName(String fullName);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT u.*, l.* FROM registration r JOIN learner l ON r.learner_id = l.id JOIN user u ON u.id = l.id WHERE r.course_id = UNHEX(REPLACE(:courseId, '-', ''))", nativeQuery = true)
    List<Learner> findLearnsByCourseId(String courseId);

    @Query(value = "SELECT u.*,l.* FROM learner l JOIN user u On l.id = u.id WHERE LOWER(u.full_name) LIKE LOWER(CONCAT('%', :name, '%'))", nativeQuery = true)
    List<Learner> findLearnerByName(@Param("name") String name);
}
