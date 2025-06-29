package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.ChapterDTO;

import java.util.List;


public interface ChapterService {
    List<ChapterDTO> getAllChapters();
    ChapterDTO updateChapter(ChapterDTO chapterDTO, String id);
    ChapterDTO createChapter(ChapterDTO chapterDTO);
    ChapterDTO getChapterById(String id);
    void deleteChapter(String id);
    List<ChapterDTO> getChaptersByCourseId(String courseId);
}
