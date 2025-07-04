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
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Override
    public List<SubmissionDTO> getAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream()
                .map(submission -> modelMapper.map(submission, SubmissionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionDTO getSubmissionById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Submission> optionalSubmission = submissionRepository.findById(uuid);
        return optionalSubmission.map(submission -> modelMapper.map(submission, SubmissionDTO.class)).orElse(null);
    }

    @Override
    public SubmissionDTO createSubmission(SubmissionDTO submissionDTO) {
        UUID learnerId = UUID.fromString(submissionDTO.getLearnerId());
        UUID lessonId = UUID.fromString(submissionDTO.getLessonId());

        Learner learner = learnerRepository.findById(learnerId).orElse(null);
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);

        Submission submission = modelMapper.map(submissionDTO, Submission.class);
        Instant now = Instant.now();
        submission.setFile_Url(submissionDTO.getFile_Url());
        submission.setStatus(SubmisstionStatus.PENDING);
        submission.setLearner(learner);
        submission.setLesson(lesson);
        submission.setSubmittedAt(now);
        submission.setScore(null);

        return modelMapper.map(submissionRepository.save(submission), SubmissionDTO.class);
    }

    @Override
    public SubmissionDTO updateSubmission(SubmissionDTO submissionDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Submission existingSubmission = submissionRepository.findById(uuid).orElse(null);
        existingSubmission.setFile_Url(submissionDTO.getFile_Url());
        existingSubmission.setStatus(SubmisstionStatus.valueOf(submissionDTO.getStatus()));
        existingSubmission.setSubmittedAt(Instant.now());
        return modelMapper.map(submissionRepository.save(existingSubmission), SubmissionDTO.class);

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
                .map(submission -> modelMapper.map(submission, SubmissionDTO.class))
                .collect(Collectors.toList());
    }
}
