package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.UserStatus;
import com.example.coursemanagement.models.Admin;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.dto.AdminDTO;
import com.example.coursemanagement.repositories.AdminRepository;
import com.example.coursemanagement.services.AdminService;
import com.example.coursemanagement.services.exceptions.errors.DuplicateResourceException;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;


    @Override
    public AdminDTO getAdminById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Admin> optionalAdmin = adminRepository.findById(uuid);
        return optionalAdmin.map(admin -> modelMapper.map(admin, AdminDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Admin"));
    }


    @Override
    public AdminDTO updateAdmin(AdminDTO adminDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Admin existingAdmin = adminRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Admin"));
        modelMapper.map(adminDTO, existingAdmin);
        return modelMapper.map(adminRepository.save(existingAdmin), AdminDTO.class);
    }
}
