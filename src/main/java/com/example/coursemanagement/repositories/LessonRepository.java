package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> getLessonsByChapterId(UUID chapterId);
}
