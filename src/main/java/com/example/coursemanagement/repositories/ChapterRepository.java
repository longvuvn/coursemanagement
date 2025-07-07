package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ChapterRepository extends JpaRepository<Chapter, UUID> {
    List<Chapter> getChaptersByCourseId(UUID courseId);

    //tìm chapter theo title
    @Query("SELECT c FROM Chapter c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Chapter> findChapterByTitle(@Param("title") String title);

}
