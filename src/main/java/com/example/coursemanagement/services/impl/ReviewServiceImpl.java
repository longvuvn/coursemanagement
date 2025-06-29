package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.ReviewStatus;
import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.Learner;
import com.example.coursemanagement.models.Review;
import com.example.coursemanagement.models.dto.ReviewDTO;
import com.example.coursemanagement.repositories.CourseRepository;
import com.example.coursemanagement.repositories.LearnerRepository;
import com.example.coursemanagement.repositories.ReviewRepository;
import com.example.coursemanagement.services.CourseService;
import com.example.coursemanagement.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final LearnerRepository learnerRepository;
    private final CourseService courseService;

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(this::reviewToReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Review> review = reviewRepository.findById(uuid);
        return review.map(this::reviewToReviewDTO).orElse(null);
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        UUID learner_Id = UUID.fromString(reviewDTO.getLearnerId());
        UUID course_Id = UUID.fromString(reviewDTO.getCourseId());
        Course course = courseRepository.findById(course_Id).orElse(null);
        Learner learner = learnerRepository.findById(learner_Id).orElse(null);
        Instant now = Instant.now();
        review.setComment(reviewDTO.getComment());
        review.setRating(Integer.parseInt(reviewDTO.getRating()));
        review.setLearner(learner);
        review.setStatus(ReviewStatus.APPROVED);
        review.setCourse(course);
        review.setCreatedAt(now);
        review.setUpdatedAt(now);
        Review saved = reviewRepository.save(review);
        courseService.updateTotalRating(String.valueOf(course.getId()));
        return reviewToReviewDTO(saved);
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Review existingReview = reviewRepository.findById(uuid).orElse(null);
        existingReview.setComment(reviewDTO.getComment());
        existingReview.setRating(Integer.parseInt(reviewDTO.getRating()));
        existingReview.setUpdatedAt(Instant.now());
        return reviewToReviewDTO(reviewRepository.save(existingReview));
    }

    @Override
    public void deleteReview(String id) {
        UUID uuid = UUID.fromString(id);
        Review existingReview = reviewRepository.findById(uuid).orElse(null);
        reviewRepository.delete(existingReview);
    }

    public ReviewDTO reviewToReviewDTO(Review review){
        ReviewDTO dto = new ReviewDTO();
        dto.setId(String.valueOf(review.getId()));
        dto.setCourseId(String.valueOf(review.getCourse().getId()));
        dto.setLearnerId(String.valueOf(review.getLearner().getId()));
        dto.setCourseName(review.getCourse().getTitle());
        dto.setLearnerName(review.getLearner().getFullName());
        dto.setComment(review.getComment());
        dto.setStatus(review.getStatus().name());
        dto.setRating(String.valueOf(review.getRating()));
        dto.setCreatedAt(String.valueOf(review.getCreatedAt()));
        dto.setUpdatedAt(String.valueOf(review.getUpdatedAt()));
        return dto;
    }
}
