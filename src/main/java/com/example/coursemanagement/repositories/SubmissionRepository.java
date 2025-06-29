package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    List<Submission> getSubmissionByLessonId(UUID learnerId);
}
