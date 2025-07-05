package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    // lấy tất cả khóa học của learner
    @Query("SELECT r.course FROM Registration r WHERE r.learner.id = :learnerId")
    List<Course> findCoursesByLearnerId(@Param("learnerId") UUID learnerId);

    // tìm khóa học theo title
    @Query("SELECT c FROM Course c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Course> findCourseByTitle(@Param("title") String title);

    // tìm khóa học mới nhất
    @Query("SELECT c FROM Course c ORDER BY c.createdAt DESC")
    List<Course> findLatestCourses();

    // tìm khóa học cũ nhất
    @Query("SELECT c FROM Course c ORDER BY c.createdAt ASC")
    List<Course> findOldestCourses();
}
