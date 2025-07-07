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
    @Query("SELECT l FROM Lesson l WHERE LOWER(l.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Lesson> findLessonByTitle(@Param("title") String title);
}
