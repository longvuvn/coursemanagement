package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    // lấy tất cả khóa học của learner
    @Query(value = "SELECT c.* FROM registration r JOIN course c ON r.course_id = c.id WHERE r.learner_id = UNHEX(REPLACE(:learnerId, '-', ''))", nativeQuery = true)
    List<Course> findCoursesByLearnerId(String learnerId);

    // tìm khóa học theo title
    @Query(value = "SELECT c.* FROM course c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))", nativeQuery = true)
    List<Course> findCourseByTitle(@Param("title") String title);

    // tìm khóa học mới nhất
    @Query(value = "SELECT c.* FROM course c ORDER BY c.created_at DESC", nativeQuery = true)
    List<Course> findLatestCourses();

    // tìm khóa học cũ nhất
    @Query(value = "SELECT c.* FROM course c ORDER BY c.created_at ASC", nativeQuery = true)
    List<Course> findOldestCourses();
}
