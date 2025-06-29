package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    ReviewDTO getReviewById(String id);
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO updateReview(ReviewDTO reviewDTO, String id);
    void deleteReview(String id);
}
