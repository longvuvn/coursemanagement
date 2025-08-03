package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.ReviewDTO;
import com.example.coursemanagement.services.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Review Management", description = "Operations related to course reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Get all reviews")
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAll() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }

    @Operation(summary = "Get review by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getById(@PathVariable String id) {
        ReviewDTO review = reviewService.getReviewById(id);
        return ResponseEntity.status(HttpStatus.OK).body(review);
    }

    @Operation(summary = "Create a new review")
    @PostMapping
    public ResponseEntity<ReviewDTO> create(@Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO created = reviewService.createReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing review")
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> update(@PathVariable String id,
                                            @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updated = reviewService.updateReview(reviewDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @Operation(summary = "Delete review by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
