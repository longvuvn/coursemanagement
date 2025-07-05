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
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Review> optionalReview = reviewRepository.findById(uuid);
        return optionalReview.map(review -> modelMapper.map(review, ReviewDTO.class)).orElse(null);
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = modelMapper.map(reviewDTO, Review.class);
        UUID learner_Id = UUID.fromString(reviewDTO.getLearnerId());
        UUID course_Id = UUID.fromString(reviewDTO.getCourseId());
        Course course = courseRepository.findById(course_Id).orElse(null);
        Learner learner = learnerRepository.findById(learner_Id).orElse(null);
        review.setComment(reviewDTO.getComment());
        review.setRating(Integer.parseInt(reviewDTO.getRating()));
        review.setLearner(learner);
        review.setStatus(ReviewStatus.APPROVED);
        review.setCourse(course);
        Review saved = reviewRepository.save(review);
        courseService.updateTotalRating(String.valueOf(course.getId()));
        return modelMapper.map(saved, ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Review existingReview = reviewRepository.findById(uuid).orElse(null);
        existingReview.setComment(reviewDTO.getComment());
        existingReview.setRating(Integer.parseInt(reviewDTO.getRating()));
        return modelMapper.map(reviewRepository.save(existingReview), ReviewDTO.class);
    }

    @Override
    public void deleteReview(String id) {
        UUID uuid = UUID.fromString(id);
        Review existingReview = reviewRepository.findById(uuid).orElse(null);
        reviewRepository.delete(existingReview);
    }
}
