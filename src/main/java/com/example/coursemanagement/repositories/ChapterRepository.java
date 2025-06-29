package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Chapter;
import com.example.coursemanagement.models.dto.ChapterDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChapterRepository extends JpaRepository<Chapter, UUID> {
    List<Chapter> getChaptersByCourseId(UUID courseId);

}
