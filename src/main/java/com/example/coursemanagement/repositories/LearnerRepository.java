package com.example.coursemanagement.repositories;

import com.example.coursemanagement.enums.UserStatus;
import com.example.coursemanagement.models.Learner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LearnerRepository extends JpaRepository<Learner, UUID> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    //lấy tất cả learner trong 1 khóa học
    @Query("SELECT r.learner FROM Registration r WHERE r.course.id = :courseId")
    List<Learner> findLearnsByCourseId(@Param("courseId") UUID courseId);

    //tìm kiếm learner theo tên
    @Query("SELECT l FROM Learner l WHERE LOWER(l.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Learner> findLearnerByName(@Param("name") String name);

    Page<Learner> findByStatus(UserStatus status, Pageable pageable);
}
