package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.SubmissionDTO;

import java.util.List;

public interface SubmissionService {
    List<SubmissionDTO> getAllSubmissions();
    SubmissionDTO getSubmissionById(String id);
    SubmissionDTO createSubmission(SubmissionDTO submissionDTO);
    SubmissionDTO updateSubmission(SubmissionDTO submissionDTO, String id);
    void deleteSubmission(String id);
    List<SubmissionDTO> getSubmissionsByLessonId(String lessonId);
}
