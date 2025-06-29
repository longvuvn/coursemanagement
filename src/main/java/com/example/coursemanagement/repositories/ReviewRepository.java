package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query("SELECT SUM(r.rating) FROM Review r WHERE r.course.id = :courseId")
    int sumRatingByCourseId(@Param("courseId") UUID courseId);

    int countReviewsByCourseId(@Param("courseId") UUID courseId);
}
