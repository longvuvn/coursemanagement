package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.ReviewDTO;
import com.example.coursemanagement.services.ReviewService;
import com.example.coursemanagement.services.exceptions.errors.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // Lấy tất cả reviews
    @GetMapping
    public ResponseEntity<APIResponse<List<ReviewDTO>>> getAll() {
        try {
            List<ReviewDTO> reviews = reviewService.getAllReviews();
            APIResponse<List<ReviewDTO>> response = new APIResponse<>(
                    "success",
                    "Reviews retrieved successfully",
                    reviews,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            APIResponse<List<ReviewDTO>> response = new APIResponse<>(
                    "error",
                    "Failed to retrieve reviews",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Lấy review theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ReviewDTO>> getById(@PathVariable String id) {
        try {
            ReviewDTO review = reviewService.getReviewById(id);
            APIResponse<ReviewDTO> response = new APIResponse<>(
                    "success",
                    "Review retrieved successfully",
                    review,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            APIResponse<ReviewDTO> response = new APIResponse<>(
                    "error",
                    "Failed to retrieve review",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Tạo review
    @PostMapping
    public ResponseEntity<APIResponse<ReviewDTO>> create(@Valid @RequestBody ReviewDTO reviewDTO) {
        try {
            ReviewDTO created = reviewService.createReview(reviewDTO);
            APIResponse<ReviewDTO> response = new APIResponse<>(
                    "success",
                    "Review created successfully",
                    created,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            APIResponse<ReviewDTO> response = new APIResponse<>(
                    "error",
                    "Failed to create review",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Chỉnh sửa review
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ReviewDTO>> update(@PathVariable String id, @RequestBody ReviewDTO reviewDTO) {
        try {
            ReviewDTO updated = reviewService.updateReview(reviewDTO, id);
            APIResponse<ReviewDTO> response = new APIResponse<>(
                    "success",
                    "Review updated successfully",
                    updated,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            APIResponse<ReviewDTO> response = new APIResponse<>(
                    "error",
                    "Failed to update review",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Xóa review
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try {
            reviewService.deleteReview(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Review deleted successfully",
                    null,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception ex) {
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Failed to delete review",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

