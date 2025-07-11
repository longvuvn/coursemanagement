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
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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
    private final ModelMapper modelMapper;

    @Override
    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findByStatus(LessonStatus.ACTIVE);
        return lessons.stream()
                .map(lesson -> modelMapper.map(lesson, LessonDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public LessonDTO getLessonById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Lesson> optionalLesson = lessonRepository.findById(uuid);
        LessonDTO lessonDTO = optionalLesson.map(lesson -> modelMapper.map(lesson, LessonDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Lesson"));
        List<SubmissionDTO> submissionDTOS = submissionService.getSubmissionsByLessonId(uuid.toString());
        lessonDTO.setSubmissions(submissionDTOS);
        return lessonDTO;
    }

    @Override
    public LessonDTO createLesson(LessonDTO lessonDTO) {
        UUID uuid = UUID.fromString(lessonDTO.getChapterId());
        Chapter chapter = chapterRepository.findById(uuid).orElse(null);
        Lesson lesson = modelMapper.map(lessonDTO, Lesson.class);
        lesson.setStatus(LessonStatus.ACTIVE);
        lesson.setChapter(chapter);
        return modelMapper.map(lessonRepository.save(lesson), LessonDTO.class);
    }

    @Override
    public LessonDTO updateLesson(LessonDTO lessonDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Lesson existingLesson = lessonRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Lesson"));
        modelMapper.map(lessonDTO, existingLesson);
        return modelMapper.map(lessonRepository.save(existingLesson), LessonDTO.class);
    }

    @Override
    public void deleteLesson(String id) {
        UUID uuid = UUID.fromString(id);
        Lesson existingLesson = lessonRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Lesson"));
        lessonRepository.delete(existingLesson);
    }

    @Override
    public List<LessonDTO> getLessonsByChapterId(String chapterId) {
        UUID uuid = UUID.fromString(chapterId);
        List<Lesson> lessons = lessonRepository.getLessonsByChapterId(uuid);
        return lessons.stream()
                .map(lesson -> modelMapper.map(lesson, LessonDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonDTO> getLessonsByTitle(String title) {
        List<Lesson> lessonList = lessonRepository.findLessonByTitle(title);
        return lessonList.stream()
                .map(lesson -> modelMapper.map(lesson, LessonDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonDTO> getLatestLessons() {
        List<Lesson> lessonList = lessonRepository.findAll(Sort.by("createdAt").descending());
        return lessonList.stream()
                .map(lesson -> modelMapper.map(lesson, LessonDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonDTO> getOldestLessons() {
        List<Lesson> lessonList = lessonRepository.findAll(Sort.by("createdAt").ascending());
        return lessonList.stream()
                .map(lesson -> modelMapper.map(lesson, LessonDTO.class) )
                .collect(Collectors.toList());
    }
}
