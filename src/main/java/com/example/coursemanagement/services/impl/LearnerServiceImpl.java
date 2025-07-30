package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.LearnerLevel;
import com.example.coursemanagement.enums.UserStatus;
import com.example.coursemanagement.models.Learner;
import com.example.coursemanagement.models.Pagination;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.repositories.LearnerRepository;
import com.example.coursemanagement.repositories.RegistrationRepository;
import com.example.coursemanagement.services.EmailService;
import com.example.coursemanagement.services.LearnerService;
import com.example.coursemanagement.services.exceptions.errors.DuplicateResourceException;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearnerServiceImpl implements LearnerService {

    private final LearnerRepository learnerRepository;
    private static final String DEFAULT_AVATAR_PATH = "/data/images/c21f969b5f03d33d43e04f8f136e7682.png";
    private final RoleServiceImpl roleService;
    private final RegistrationRepository registrationRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    @Override
    public Pagination<LearnerDTO> getAllLearners(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Learner> learnersPage  = learnerRepository.findByStatus(UserStatus.ACTIVE, pageable);

        List<LearnerDTO> learnerDTOs = learnersPage.getContent()
                .stream()
                .map(learner -> modelMapper.map(learner, LearnerDTO.class))
                .collect(Collectors.toList());

        Pagination<LearnerDTO> pagination = new Pagination<>();
        pagination.setContent(learnerDTOs);
        pagination.setPage(learnersPage.getNumber());
        pagination.setSize(learnersPage.getSize());
        pagination.setTotalPages(learnersPage.getTotalPages());
        pagination.setTotalElements(learnersPage.getTotalElements());

        return pagination;
    }

    @Override
    public LearnerDTO getLearnerById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Learner> optionalLearner = learnerRepository.findById(uuid);
        return optionalLearner.map(learner -> modelMapper.map(learner, LearnerDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Learner"));
    }

    @Override
    public LearnerDTO createLearner(LearnerDTO learnerDTO) {
        Learner learner = modelMapper.map(learnerDTO, Learner.class);
        String rawPassword = emailService.generateRandomPassword();
        Role role = roleService.getRoleByName("Learner");
        if (!StringUtils.hasText(learnerDTO.getAvatar())) {
            learnerDTO.setAvatar(DEFAULT_AVATAR_PATH);
        }
        if (learnerRepository.existsByEmail(learnerDTO.getEmail().trim())) {
            throw new DuplicateResourceException("Email không hợp lệ");
        }
        if (learnerRepository.existsByPhoneNumber(learnerDTO.getPhoneNumber().trim())) {
            throw new DuplicateResourceException("Số điện thoại không hợp lệ");
        }
        learner.setPassword(passwordEncoder.encode(rawPassword));
        learner.setRole(role);
        learner.setStatus(UserStatus.ACTIVE);
        learner.setLevel(String.valueOf(LearnerLevel.BEGINNER));
        learner.setAvatar(learnerDTO.getAvatar());
        Learner savedLearner = learnerRepository.save(learner);
        emailService.sendEmail(learner.getEmail(), learner.getFullName(), rawPassword);
        return modelMapper.map(savedLearner, LearnerDTO.class);
    }

    @Override
    public LearnerDTO updateLearner(LearnerDTO learnerDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Learner existingLearner = learnerRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Learner"));
        modelMapper.map(learnerDTO, existingLearner);
        existingLearner.setRole(roleService.getRoleByName(learnerDTO.getRoleName()));
        return modelMapper.map(learnerRepository.save(existingLearner), LearnerDTO.class);
    }

    @Override
    public LearnerDTO updateTotalCourses(String learnerId) {
        UUID uuid = UUID.fromString(learnerId);
        int totalCourse = registrationRepository.countByLearnerId(uuid);

        Learner learner = learnerRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Learner"));
        if (learner != null) {
            learner.setTotalCourses(totalCourse);
            learnerRepository.save(learner);
        }
        return null;
    }

    @Override
    public void deleteLearner(String id) {
        UUID uuid = UUID.fromString(id);
        Learner existingLearner = learnerRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Learner"));
        learnerRepository.delete(existingLearner);
    }

    @Override
    public List<LearnerDTO> getLearnersByCourseId(UUID courseId) {
        List<Learner> learners = learnerRepository.findLearnsByCourseId(courseId);
        return learners.stream()
                .map(learner -> modelMapper.map(learner, LearnerDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public List<LearnerDTO> getLearnerByName(String name) {
        List<Learner> learners = learnerRepository.findLearnerByName(name);
        if(learners.isEmpty()){
            throw new ResourceNotFoundException("Not Found This Learner");
        }
        return learners.stream()
                .map(learner -> modelMapper.map(learner, LearnerDTO.class) )
                .collect(Collectors.toList());
    }
}
