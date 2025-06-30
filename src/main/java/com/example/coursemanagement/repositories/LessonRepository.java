package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> getLessonsByChapterId(UUID chapterId);

    //tim kiếm lesson theo title
    @Query(value = "SELECT l.* FROM lesson l WHERE LOWER(l.title) LIKE LOWER(CONCAT('%', :title, '%'))", nativeQuery = true)
    List<Lesson> findLessonByTitle(@Param("title") String title);

    // tìm lesson mới nhất
    @Query(value = "SELECT l.* FROM lesson l ORDER BY l.created_at DESC", nativeQuery = true)
    List<Lesson> findLatestLessons();

    //tìm lesson cũ nhất
    @Query(value = "SELECT l.* FROM lesson l ORDER BY l.created_at ASC", nativeQuery = true)
    List<Lesson> findOldestLessons();
}
