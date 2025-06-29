package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.SubmisstionStatus;
import com.example.coursemanagement.models.Learner;
import com.example.coursemanagement.models.Lesson;
import com.example.coursemanagement.models.Submission;
import com.example.coursemanagement.models.dto.SubmissionDTO;
import com.example.coursemanagement.repositories.LearnerRepository;
import com.example.coursemanagement.repositories.LessonRepository;
import com.example.coursemanagement.repositories.SubmissionRepository;
import com.example.coursemanagement.services.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final LearnerRepository learnerRepository;
    private final LessonRepository lessonRepository;

    @Override
    public List<SubmissionDTO> getAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream()
                .map(this::submissionToSubmissionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionDTO getSubmissionById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Submission> submission = submissionRepository.findById(uuid);
        return submission.map(this::submissionToSubmissionDTO).orElse(null);
    }

    @Override
    public SubmissionDTO createSubmission(SubmissionDTO submissionDTO) {
        UUID learnerId = UUID.fromString(submissionDTO.getLearnerId());
        UUID lessonId = UUID.fromString(submissionDTO.getLessonId());

        Learner learner = learnerRepository.findById(learnerId).orElse(null);
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);

        Submission submission = new Submission();
        Instant now = Instant.now();
        submission.setFile_Url(submissionDTO.getFile_Url());
        submission.setStatus(SubmisstionStatus.PENDING);
        submission.setLearner(learner);
        submission.setLesson(lesson);
        submission.setSubmittedAt(now);
        submission.setScore(null);

        return submissionToSubmissionDTO(submissionRepository.save(submission));
    }

    @Override
    public SubmissionDTO updateSubmission(SubmissionDTO submissionDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Submission existingSubmission = submissionRepository.findById(uuid).orElse(null);
        existingSubmission.setFile_Url(submissionDTO.getFile_Url());
        existingSubmission.setStatus(SubmisstionStatus.valueOf(submissionDTO.getStatus()));
        existingSubmission.setSubmittedAt(Instant.now());
        return submissionToSubmissionDTO(submissionRepository.save(existingSubmission));

    }

    @Override
    public void deleteSubmission(String id) {
        UUID uuid = UUID.fromString(id);
        Submission existingSubmission = submissionRepository.findById(uuid).orElse(null);
        submissionRepository.delete(existingSubmission);
    }

    @Override
    public List<SubmissionDTO> getSubmissionsByLessonId(String lessonIdd) {
        UUID uuid = UUID.fromString(lessonIdd);
        List<Submission> submissions = submissionRepository.getSubmissionByLessonId(uuid);
        return submissions.stream()
                .map(this::submissionToSubmissionDTO)
                .collect(Collectors.toList());
    }

    public SubmissionDTO submissionToSubmissionDTO(Submission submission){
        SubmissionDTO dto = new SubmissionDTO();
        dto.setId(String.valueOf(submission.getId()));
        dto.setStatus(submission.getStatus().name());
        dto.setLearnerId(String.valueOf(submission.getLearner().getId()));
        dto.setLessonId(String.valueOf(submission.getLesson().getId()));
        dto.setLearnerName(submission.getLearner().getFullName());
        dto.setLessonName(submission.getLesson().getTitle());
        dto.setFile_Url(submission.getFile_Url());
        dto.setSubmissionAt(String.valueOf(submission.getSubmittedAt()));
        return dto;
    }
}
