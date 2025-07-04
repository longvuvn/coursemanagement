package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.LearnerDTO;

import java.util.List;
import java.util.UUID;

public interface LearnerService {
    List<LearnerDTO> getAllLearners();
    LearnerDTO getLearnerById(String id);
    LearnerDTO createLearner(LearnerDTO learnerDTO);
    LearnerDTO updateLearner(LearnerDTO learnerDTO, String id);
    LearnerDTO updateTotalCourses(String learnerId);
    void deleteLearner(String id);
    List<LearnerDTO> getLearnersByCourseId(UUID courseId);
    List<LearnerDTO> getLearnerByName(String name);
}
