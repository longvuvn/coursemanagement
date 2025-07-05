package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.CourseStatus;
import com.example.coursemanagement.models.Category;
import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.dto.ChapterDTO;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.repositories.CategoryRepository;
import com.example.coursemanagement.repositories.CourseRepository;
import com.example.coursemanagement.repositories.ReviewRepository;
import com.example.coursemanagement.services.ChapterService;
import com.example.coursemanagement.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final ChapterService chapterService;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Course> optionalCourse = courseRepository.findById(uuid);
        CourseDTO courseDTO = optionalCourse.map(course -> modelMapper.map(course, CourseDTO.class)).orElse(null);
        List<ChapterDTO> chapterDTO = chapterService.getChaptersByCourseId(uuid.toString());
        courseDTO.setChapters(chapterDTO);
        return courseDTO;
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        Category category = categoryRepository.findByName(courseDTO.getCategoryName());
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setCategory(category);
        course.setPrice(new BigDecimal(courseDTO.getPrice()));
        course.setStatus(CourseStatus.ACTIVE);
        course.setImage(courseDTO.getImage());
        course.setTotalReviews(0);
        course.setAverageRating(0.0);
        Course savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseDTO.class);
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Category category = categoryRepository.findByName(courseDTO.getCategoryName());
        Course existingCourse = courseRepository.findById(uuid).orElse(null);
        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setCategory(category);
        existingCourse.setPrice(new BigDecimal(courseDTO.getPrice()));
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setImage(courseDTO.getImage());
        existingCourse.setStatus(CourseStatus.valueOf(courseDTO.getStatus()));
        existingCourse.setTotalReviews(courseDTO.getTotalReviews());
        return modelMapper.map(courseRepository.save(existingCourse), CourseDTO.class);
    }

    @Override
    public void deleteCourse(String id) {
        UUID uuid = UUID.fromString(id);
        Course existingCourse = courseRepository.findById(uuid).orElse(null);
        courseRepository.delete(existingCourse);
    }

    @Override
    public CourseDTO updateTotalRating(String courseId) {

        UUID courseUUID = UUID.fromString(courseId);
        Course course = courseRepository.findById(courseUUID).orElse(null);
        int totalRating = reviewRepository.sumRatingByCourseId(courseUUID);
        int totalReviews = reviewRepository.countReviewsByCourseId(courseUUID);
        course.setTotalReviews(totalReviews);
        course.setAverageRating(totalReviews == 0 ? 0.0 : (double) totalRating / totalReviews);
        courseRepository.save(course);
        return modelMapper.map(course, CourseDTO.class);

    }
    @Override
    public List<CourseDTO> getCoursesByLearnerId(UUID learnerId) {
        List<Course> courseList = courseRepository.findCoursesByLearnerId(learnerId);
        return courseList.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByTitle(String title) {
        List<Course> courseList = courseRepository.findCourseByTitle(title);
        return courseList.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getLatestCourses() {
        List<Course> courseList = courseRepository.findLatestCourses();
        return courseList.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getOldestCourses() {
        List<Course> courseList = courseRepository.findOldestCourses();
        return courseList.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class) )
                .collect(Collectors.toList());
    }
}
