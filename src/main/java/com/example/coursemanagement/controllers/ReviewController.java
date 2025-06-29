package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.ReviewDTO;
import com.example.coursemanagement.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<APIResponse<List<ReviewDTO>>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        APIResponse<List<ReviewDTO>> response = new APIResponse<>(
                "success",
                "Reviews retrieved successfully",
                reviews,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ReviewDTO>> getReviewById(@PathVariable String id) {
        ReviewDTO review = reviewService.getReviewById(id);
        APIResponse<ReviewDTO> response = new APIResponse<>(
                "success",
                "Review retrieved successfully",
                review,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<ReviewDTO>> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO created = reviewService.createReview(reviewDTO);
        APIResponse<ReviewDTO> response = new APIResponse<>(
                "success",
                "Review created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ReviewDTO>> updateReview(@PathVariable String id, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updated = reviewService.updateReview(reviewDTO, id);
        APIResponse<ReviewDTO> response = new APIResponse<>(
                "success",
                "Review updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Review deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
