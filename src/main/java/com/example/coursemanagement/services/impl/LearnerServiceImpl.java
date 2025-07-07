package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.LearnerLevel;
import com.example.coursemanagement.enums.UserStatus;
import com.example.coursemanagement.models.Learner;
import com.example.coursemanagement.models.Pagination;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.repositories.LearnerRepository;
import com.example.coursemanagement.repositories.RegistrationRepository;
import com.example.coursemanagement.services.LearnerService;
import com.example.coursemanagement.services.exceptions.error.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
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

    @Override
    public Pagination<LearnerDTO> getAllLearners(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Learner> learnersPage  = learnerRepository.findAll(pageable);

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
        return optionalLearner.map(learner -> modelMapper.map(learner, LearnerDTO.class)).orElse(null);
    }

    @Override
    public LearnerDTO createLearner(LearnerDTO learnerDTO) {
        Learner learner = modelMapper.map(learnerDTO, Learner.class);
        Role role = roleService.getRoleByName("Learner");
        if (learnerDTO.getAvatar() == null || learnerDTO.getAvatar().isEmpty()) {
            learnerDTO.setAvatar(DEFAULT_AVATAR_PATH);
        }
        if (learnerRepository.existsByEmail(learnerDTO.getEmail().trim())) {
            throw new DuplicateResourceException("Email không hợp lệ");
        }
        if (learnerRepository.existsByPhoneNumber(learnerDTO.getPhoneNumber().trim())) {
            throw new DuplicateResourceException("Số điện thoại không hợp lệ");
        }
        learner.setFullName(learnerDTO.getFullName());
        learner.setEmail(learnerDTO.getEmail());
        learner.setPhoneNumber(learnerDTO.getPhoneNumber());
        learner.setPassword(passwordEncoder.encode(learnerDTO.getPassword()));
        learner.setAvatar(learnerDTO.getAvatar());
        learner.setRole(role);
        learner.setStatus(UserStatus.ACTIVE);
        learner.setLevel(String.valueOf(LearnerLevel.BEGINNER));
        learner.setTotalCourses(0);
        return modelMapper.map(learnerRepository.save(learner), LearnerDTO.class);
    }

    @Override
    public LearnerDTO updateLearner(LearnerDTO learnerDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Learner existingLearner = learnerRepository.findById(uuid).orElse(null);
        existingLearner.setFullName(learnerDTO.getFullName());
        existingLearner.setEmail(learnerDTO.getEmail());
        existingLearner.setPhoneNumber(learnerDTO.getPhoneNumber());
        existingLearner.setLevel(learnerDTO.getLevel());
        existingLearner.setStatus(UserStatus.valueOf(learnerDTO.getStatus()));
        existingLearner.setTotalCourses(learnerDTO.getTotalCourses());
        existingLearner.setAvatar(learnerDTO.getAvatar());
        existingLearner.setPassword(learnerDTO.getPassword());
        existingLearner.setTotalCourses(learnerDTO.getTotalCourses());
        existingLearner.setRole(roleService.getRoleByName(learnerDTO.getRoleName()));
        return modelMapper.map(learnerRepository.save(existingLearner), LearnerDTO.class);
    }

    @Override
    public LearnerDTO updateTotalCourses(String learnerId) {
        UUID uuid = UUID.fromString(learnerId);
        int totalCourse = registrationRepository.countByLearnerId(uuid);

        Learner learner = learnerRepository.findById(uuid).orElse(null);
        if (learner != null) {
            learner.setTotalCourses(totalCourse);
            learnerRepository.save(learner);
        }
        return null;
    }

    @Override
    public void deleteLearner(String id) {
        UUID uuid = UUID.fromString(id);
        Learner existingLearner = learnerRepository.findById(uuid).orElse(null);
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
        return learners.stream()
                .map(learner -> modelMapper.map(learner, LearnerDTO.class) )
                .collect(Collectors.toList());
    }
}
