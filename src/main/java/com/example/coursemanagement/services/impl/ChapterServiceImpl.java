package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.ChapterStatus;
import com.example.coursemanagement.models.Chapter;
import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.dto.ChapterDTO;
import com.example.coursemanagement.models.dto.LessonDTO;
import com.example.coursemanagement.repositories.ChapterRepository;
import com.example.coursemanagement.repositories.CourseRepository;
import com.example.coursemanagement.services.ChapterService;
import com.example.coursemanagement.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private final LessonService lessonService;

    @Override
    public List<ChapterDTO> getAllChapters() {
        List<Chapter> chapters = chapterRepository.findAll();
        return chapters.stream()
                .map(this::chapterToChapterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ChapterDTO updateChapter(ChapterDTO chapterDTO, String id) {
        Instant now = Instant.now();
        UUID uuid = UUID.fromString(id);
        Chapter existingChapter = chapterRepository.findById(uuid).orElse(null);
        existingChapter.setTitle(chapterDTO.getTitle());
        existingChapter.setStatus(ChapterStatus.valueOf(chapterDTO.getStatus()));
        existingChapter.setUpdatedAt(now);
        return chapterToChapterDTO(chapterRepository.save(existingChapter));
    }

    @Override
    public ChapterDTO createChapter(ChapterDTO chapterDTO) {
        UUID uuid = UUID.fromString(chapterDTO.getCourseId());
        Course course = courseRepository.findById(uuid).orElse(null);
        Chapter chapter = new Chapter();
        Instant now = Instant.now();
        chapter.setTitle(chapterDTO.getTitle());
        chapter.setStatus(ChapterStatus.ACTIVE);
        chapter.setCourse(course);
        chapter.setCreatedAt(now);
        chapter.setUpdatedAt(now);
        return chapterToChapterDTO(chapterRepository.save(chapter));
    }

    @Override
    public ChapterDTO getChapterById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Chapter> chapter = chapterRepository.findById(uuid);
        ChapterDTO chapterDTO = chapter.map(this::chapterToChapterDTO).orElse(null);
        List<LessonDTO> lessonDTOS = lessonService.getLessonsByChapterId(uuid.toString());
        chapterDTO.setLessons(lessonDTOS);
        return chapterDTO;
    }

    @Override
    public List<ChapterDTO> getChaptersByCourseId(String courseId) {
        UUID uuid = UUID.fromString(courseId);
        List<Chapter> chapters = chapterRepository.getChaptersByCourseId(uuid);
        return chapters.stream()
                .map(this::chapterToChapterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChapterDTO> getChapterByTitle(String title) {
        List<Chapter> chapters = chapterRepository.findChapterByTitle(title);
        return chapters.stream()
                .map(this::chapterToChapterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChapterDTO> getLatestChapters() {
        List<Chapter> chapterList = chapterRepository.findLatestChapters();
        return chapterList.stream()
                .map(this::chapterToChapterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChapterDTO> getOldestChapters() {
        List<Chapter> chapterList = chapterRepository.findOldestChpaters();
        return chapterList.stream()
                .map(this::chapterToChapterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteChapter(String id) {
        UUID uuid = UUID.fromString(id);
        Chapter existingChapter = chapterRepository.findById(uuid).orElse(null);
        chapterRepository.delete(existingChapter);
    }

    public ChapterDTO chapterToChapterDTO(Chapter chapter) {
        ChapterDTO dto = new ChapterDTO();
        dto.setId(String.valueOf(chapter.getId()));
        dto.setTitle(chapter.getTitle());
        dto.setStatus(chapter.getStatus().name());
        dto.setCreatedAt(String.valueOf(chapter.getCreatedAt()));
        dto.setCourseId(String.valueOf(chapter.getCourse().getId()));
        List<LessonDTO> chapters = lessonService.getLessonsByChapterId(String.valueOf(chapter.getId()));
        if (chapters != null && !chapters.isEmpty()) {
            dto.setLessons(new ArrayList<>());
        } else {
            dto.setLessons(null);
        }
        dto.setCourseName(chapter.getCourse().getTitle());
        dto.setUpdatedAt(String.valueOf(chapter.getUpdatedAt()));
        return dto;
    }
}
