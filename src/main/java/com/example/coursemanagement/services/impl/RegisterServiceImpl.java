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
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ModelMapper modelMapper;

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(registration -> modelMapper.map(registration, RegistrationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RegistrationDTO getRegistrationById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Registration> optionalRegistration = registrationRepository.findById(uuid);
        return optionalRegistration.map(registration -> modelMapper.map(registration, RegistrationDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Registration"));
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegistrationDTO createRegistration(RegistrationDTO registrationDTO) {
        UUID learnerId = UUID.fromString(registrationDTO.getLearnerId());
        UUID courseId = UUID.fromString(registrationDTO.getCourseId());

        Learner learner = learnerRepository.findById(learnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Learner"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Course"));

        Registration registration = modelMapper.map(registrationDTO, Registration.class);
        registration.setLearner(learner);
        registration.setCourse(course);
        registration.setStatus(RegistrationStatus.PENDING);
        registration.setRegisteredAt(Instant.now());
        Registration saved = registrationRepository.save(registration);
        learnerService.updateTotalCourses(registrationDTO.getLearnerId());
        return modelMapper.map(saved, RegistrationDTO.class);
    }

    @Override
    public RegistrationDTO updateRegistration(RegistrationDTO registrationDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Registration existingRegistration = registrationRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        existingRegistration.setStatus(RegistrationStatus.valueOf(registrationDTO.getStatus()));
        return modelMapper.map(registrationRepository.save(existingRegistration), RegistrationDTO.class);
    }

    @Override
    public void deleteRegistration(String id) {
        UUID uuid = UUID.fromString(id);
        Registration existingRegistration = registrationRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        registrationRepository.delete(existingRegistration);

    }
}
