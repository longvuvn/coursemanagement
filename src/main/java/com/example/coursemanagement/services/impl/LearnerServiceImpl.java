package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.LearnerLevel;
import com.example.coursemanagement.enums.UserStatus;
import com.example.coursemanagement.models.Learner;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.repositories.LearnerRepository;
import com.example.coursemanagement.repositories.RegistrationRepository;
import com.example.coursemanagement.services.LearnerService;
import com.example.coursemanagement.services.exceptions.error.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearnerServiceImpl implements LearnerService {
    private final LearnerRepository learnerRepository;
    private static final String DEFAULT_AVATAR_PATH = "/data/images/c21f969b5f03d33d43e04f8f136e7682.png";
    private final RoleServiceImpl roleService;
    private final RegistrationRepository registrationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<LearnerDTO> getAllLearners() {
        List<Learner> learners = learnerRepository.findAll();
        return learners.stream()
                .map(this::learnertoLearnerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LearnerDTO getLearnerById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Learner> learner = learnerRepository.findById(uuid);
        return learner.map(this::learnertoLearnerDTO).orElse(null);
    }

    @Override
    public LearnerDTO createLearner(LearnerDTO learnerDTO) {
        Learner learner = new Learner();
        Instant now = Instant.now();
        Role role = roleService.getRoleByName("Learner");
        if (learnerDTO.getAvatar() == null || learnerDTO.getAvatar().isEmpty()) {
            learnerDTO.setAvatar(DEFAULT_AVATAR_PATH);
        }
        if (learnerRepository.existsByEmail(learnerDTO.getEmail().trim())) {
            throw new DuplicateResourceException("Email đã tồn tại");
        }
        if (learnerRepository.existsByFullName(learnerDTO.getFullName().trim())) {
            throw new DuplicateResourceException("Tên người dùng đã được sử dụng");
        }
        if (learnerRepository.existsByPhoneNumber(learnerDTO.getPhoneNumber().trim())) {
            throw new DuplicateResourceException("Số điện thoại đã được sử dụng");
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
        learner.setCreatedAt(now);
        learner.setUpdatedAt(now);
        return learnertoLearnerDTO(learnerRepository.save(learner));
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
        existingLearner.setUpdatedAt(Instant.now());
        return learnertoLearnerDTO(learnerRepository.save(existingLearner));
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
    public List<LearnerDTO> getLearnersByCourseId(String courseId) {
        List<Learner> learners = learnerRepository.findLearnsByCourseId(courseId);
        return learners.stream()
                .map(this::learnertoLearnerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LearnerDTO> getLearnerByName(String name) {
        List<Learner> learners = learnerRepository.findLearnerByName(name);
        return learners.stream()
                .map(this::learnertoLearnerDTO)
                .collect(Collectors.toList());
    }

    public LearnerDTO learnertoLearnerDTO(Learner learner) {
        LearnerDTO dto = new LearnerDTO();
        dto.setId(String.valueOf(learner.getId()));
        dto.setFullName(learner.getFullName());
        dto.setEmail(learner.getEmail());
        dto.setPhoneNumber(learner.getPhoneNumber());
        dto.setPassword(learner.getPassword());
        dto.setAvatar(learner.getAvatar());
        dto.setRoleName(learner.getRole().getName());
        dto.setStatus(learner.getStatus().name());
        dto.setTotalCourses(learner.getTotalCourses());
        dto.setCreatedAt(String.valueOf(learner.getCreatedAt()));
        dto.setUpdatedAt(String.valueOf(learner.getUpdatedAt()));
        dto.setLevel(learner.getLevel());
        return dto;
    }
}
