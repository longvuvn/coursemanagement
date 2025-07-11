package com.example.coursemanagement.repositories;

import com.example.coursemanagement.enums.CourseStatus;
import com.example.coursemanagement.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //lọc course theo categoryName
    @Query("SELECT c FROM Course c JOIN c.category a WHERE a.name = :categoryName")
    Page<Course> findCourseByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

    //lấy tất cả khóa học theo status
    Page<Course> findByStatus(CourseStatus status, Pageable pageable);
}
