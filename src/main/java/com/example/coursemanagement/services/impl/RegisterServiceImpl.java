package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.RegistrationStatus;
import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.Learner;
import com.example.coursemanagement.models.Registration;
import com.example.coursemanagement.models.dto.RegistrationDTO;
import com.example.coursemanagement.repositories.CourseRepository;
import com.example.coursemanagement.repositories.LearnerRepository;
import com.example.coursemanagement.repositories.RegistrationRepository;
import com.example.coursemanagement.services.LearnerService;
import com.example.coursemanagement.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final LearnerRepository learnerRepository;
    private final CourseRepository courseRepository;
    private final LearnerService learnerService;

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(this::registrationToRegistrationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegistrationDTO getRegistrationById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Registration> registration = registrationRepository.findById(uuid);
        return registration.map(this::registrationToRegistrationDTO).orElse(null);
    }

    @Override
    public RegistrationDTO createRegistration(RegistrationDTO registrationDTO) {
        UUID learnerId = UUID.fromString(registrationDTO.getLearnerId());
        UUID courseId = UUID.fromString(registrationDTO.getCourseId());

        Learner learner = learnerRepository.findById(learnerId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        Registration registration = new Registration();
        Instant now = Instant.now();
        registration.setLearner(learner);
        registration.setCourse(course);
        registration.setStatus(RegistrationStatus.PENDING);
        registration.setRegisteredAt(now);
        Registration saved = registrationRepository.save(registration);
        learnerService.updateTotalCourses(registrationDTO.getLearnerId());
        return registrationToRegistrationDTO(saved);
    }

    @Override
    public RegistrationDTO updateRegistration(RegistrationDTO registrationDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Registration existingRegistration = registrationRepository.findById(uuid).orElse(null);
        existingRegistration.setStatus(RegistrationStatus.valueOf(registrationDTO.getStatus()));
        return registrationToRegistrationDTO(registrationRepository.save(existingRegistration));
    }


    @Override
    public void deleteRegistration(String id) {
        UUID uuid = UUID.fromString(id);
        Registration  existingRegistration = registrationRepository.findById(uuid).orElse(null);
        registrationRepository.delete(existingRegistration);

    }

    public RegistrationDTO registrationToRegistrationDTO(Registration registration){
        RegistrationDTO dto = new RegistrationDTO();
        dto.setId(String.valueOf(registration.getId()));
        dto.setStatus(registration.getStatus().name());
        dto.setLearnerId(String.valueOf(registration.getLearner().getId()));
        dto.setCourseId(String.valueOf(registration.getCourse().getId()));
        dto.setLearnerName(registration.getLearner().getFullName());
        dto.setCourseName(registration.getCourse().getTitle());
        dto.setRegistrationAt(String.valueOf(registration.getRegisteredAt()));
        return dto;
    }
}
