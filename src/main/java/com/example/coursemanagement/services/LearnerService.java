package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.LearnerDTO;

import java.util.List;

public interface LearnerService {
    List<LearnerDTO> getAllLearners();
    LearnerDTO getLearnerById(String id);
    LearnerDTO createLearner(LearnerDTO learnerDTO);
    LearnerDTO updateLearner(LearnerDTO learnerDTO, String id);
    LearnerDTO updateTotalCourses(String learnerId);
    void deleteLearner(String id);
    List<LearnerDTO> getLearnersByCourseId(String courseId);
    List<LearnerDTO> getLearnerByName(String name);
}
