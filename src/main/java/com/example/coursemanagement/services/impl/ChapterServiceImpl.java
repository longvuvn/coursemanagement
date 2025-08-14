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
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ModelMapper modelMapper;

    @Override
    public List<ChapterDTO> getAllChapters() {
        List<Chapter> chapters = chapterRepository.findByStatus(ChapterStatus.ACTIVE);
        return chapters.stream()
                .map(chapter -> modelMapper.map(chapter, ChapterDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public ChapterDTO updateChapter(ChapterDTO chapterDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Chapter existingChapter = chapterRepository.findById(uuid)
                        .orElseThrow(() -> new ResourceNotFoundException("Not Found This Course"));
        modelMapper.map(chapterDTO, existingChapter);
        existingChapter.setStatus(ChapterStatus.valueOf(chapterDTO.getStatus()));
        return modelMapper.map(chapterRepository.save(existingChapter), ChapterDTO.class);
    }

    @Override
    public ChapterDTO createChapter(ChapterDTO chapterDTO) {
        UUID uuid = UUID.fromString(chapterDTO.getCourseId());
        Course course = courseRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Course"));
        Chapter chapter = modelMapper.map(chapterDTO, Chapter.class);
        chapter.setStatus(ChapterStatus.ACTIVE);
        chapter.setCourse(course);
        return modelMapper.map(chapterRepository.save(chapter), ChapterDTO.class);
    }

    @Override
    public ChapterDTO getChapterById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Chapter> optionalChapter = chapterRepository.findById(uuid);
        ChapterDTO chapterDTO = optionalChapter.map(chapter -> modelMapper.map(chapter, ChapterDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Course"));
        List<LessonDTO> lessonDTOS = lessonService.getLessonsByChapterId(uuid.toString());
        chapterDTO.setLessons(lessonDTOS);
        return chapterDTO;
    }

    @Override
    public List<ChapterDTO> getChaptersByCourseId(String courseId) {
        UUID uuid = UUID.fromString(courseId);
        List<Chapter> chapters = chapterRepository.getChaptersByCourseId(uuid);
        return chapters.stream()
                .map(chapter -> modelMapper.map(chapter, ChapterDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public List<ChapterDTO> getChapterByTitle(String title) {
        List<Chapter> chapters = chapterRepository.findChapterByTitle(title);
        return chapters.stream()
                .map(chapter -> modelMapper.map(chapter, ChapterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChapterDTO> getLatestChapters() {
        List<Chapter> chapterList = chapterRepository.findAll(Sort.by("createdAt").descending());
        return chapterList.stream()
                .map(chapter -> modelMapper.map(chapter, ChapterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChapterDTO> getOldestChapters() {
        List<Chapter> chapterList = chapterRepository.findAll(Sort.by("createdAt").ascending());
        return chapterList.stream()
                .map(chapter -> modelMapper.map(chapter, ChapterDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteChapter(String id) {
        UUID uuid = UUID.fromString(id);
        Chapter existingChapter = chapterRepository.findById(uuid)
                        .orElseThrow(() -> new ResourceNotFoundException("Not Found This Course"));
        chapterRepository.delete(existingChapter);
    }
}
