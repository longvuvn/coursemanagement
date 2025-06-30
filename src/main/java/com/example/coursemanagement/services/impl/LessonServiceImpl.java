package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.LessonStatus;
import com.example.coursemanagement.models.Chapter;
import com.example.coursemanagement.models.Lesson;
import com.example.coursemanagement.models.dto.LessonDTO;
import com.example.coursemanagement.models.dto.SubmissionDTO;
import com.example.coursemanagement.repositories.ChapterRepository;
import com.example.coursemanagement.repositories.LessonRepository;
import com.example.coursemanagement.services.LessonService;
import com.example.coursemanagement.services.SubmissionService;
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
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final SubmissionService submissionService;

    @Override
    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        return lessons.stream()
                .map(this::lessonToLessonDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessonDTO getLessonById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Lesson> lesson = lessonRepository.findById(uuid);
        LessonDTO lessonDTO = lesson.map(this::lessonToLessonDTO).orElse(null);
        List<SubmissionDTO> submissionDTOS = submissionService.getSubmissionsByLessonId(uuid.toString());
        lessonDTO.setSubmissions(submissionDTOS);
        return lessonDTO;
    }

    @Override
    public LessonDTO createLesson(LessonDTO lessonDTO) {
        UUID uuid = UUID.fromString(lessonDTO.getChapterId());
        Chapter chapter = chapterRepository.findById(uuid).orElse(null);
        Lesson lesson = new Lesson();
        Instant now = Instant.now();
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setStatus(LessonStatus.ACTIVE);
        lesson.setContent(lessonDTO.getContent());
        lesson.setChapter(chapter);
        lesson.setReferenceLink(lessonDTO.getReferenceLink());
        lesson.setCreatedAt(now);
        lesson.setUpdatedAt(now);
        return lessonToLessonDTO(lessonRepository.save(lesson));
    }

    @Override
    public LessonDTO updateLesson(LessonDTO lessonDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Lesson existingLesson = lessonRepository.findById(uuid).orElse(null);
        existingLesson.setTitle(lessonDTO.getTitle());
        existingLesson.setStatus(LessonStatus.valueOf(lessonDTO.getStatus()));
        existingLesson.setContent(lessonDTO.getContent());
        existingLesson.setReferenceLink(lessonDTO.getReferenceLink());
        existingLesson.setUpdatedAt(Instant.now());
        return lessonToLessonDTO(lessonRepository.save(existingLesson));
    }

    @Override
    public void deleteLesson(String id) {
        UUID uuid = UUID.fromString(id);
        Lesson existingLesson = lessonRepository.findById(uuid).orElse(null);
        lessonRepository.delete(existingLesson);
    }

    @Override
    public List<LessonDTO> getLessonsByChapterId(String chapterId) {
        UUID uuid = UUID.fromString(chapterId);
        List<Lesson> lessons = lessonRepository.getLessonsByChapterId(uuid);
        return lessons.stream()
                .map(this::lessonToLessonDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonDTO> getLessonsByTitle(String title) {
        List<Lesson> lessonList = lessonRepository.findLessonByTitle(title);
        return lessonList.stream()
                .map(this::lessonToLessonDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonDTO> getLatestLessons() {
        List<Lesson> lessonList = lessonRepository.findLatestLessons();
        return lessonList.stream()
                .map(this::lessonToLessonDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonDTO> getOldestLessons() {
        List<Lesson> lessonList = lessonRepository.findOldestLessons();
        return lessonList.stream()
                .map(this::lessonToLessonDTO)
                .collect(Collectors.toList());
    }

    public LessonDTO lessonToLessonDTO(Lesson lesson){
        LessonDTO dto = new LessonDTO();
        dto.setId(String.valueOf(lesson.getId()));
        dto.setTitle(lesson.getTitle());
        dto.setChapterId(String.valueOf(lesson.getChapter().getId()));
        dto.setReferenceLink(lesson.getReferenceLink());
        dto.setContent(lesson.getContent());
        List<SubmissionDTO> submissionDTOS = submissionService.getSubmissionsByLessonId(String.valueOf(lesson.getId()));
        if (submissionDTOS != null && !submissionDTOS.isEmpty()) {
            dto.setSubmissions(new ArrayList<>());
        }else {
            dto.setSubmissions(null);
        }
        dto.setStatus(lesson.getStatus().name());
        dto.setCreatedAt(String.valueOf(lesson.getCreatedAt()));
        dto.setUpdatedAt(String.valueOf(lesson.getUpdatedAt()));
        return dto;
    }
}
