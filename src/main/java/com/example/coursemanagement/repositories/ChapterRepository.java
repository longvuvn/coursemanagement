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
    @Query(value = "SELECT c.* FROM chapter c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))", nativeQuery = true)
    List<Chapter> findChapterByTitle(@Param("title") String title);

    // tìm chapter mới nhất
    @Query(value = "SELECT c.* FROM chapter c ORDER BY c.created_at DESC", nativeQuery = true)
    List<Chapter> findLatestChapters();

    //tìm chapter cũ nhất
    @Query(value = "SELECT c.* FROM chapter c ORDER BY c.created_at ASC", nativeQuery = true)
    List<Chapter> findOldestChpaters();
}
