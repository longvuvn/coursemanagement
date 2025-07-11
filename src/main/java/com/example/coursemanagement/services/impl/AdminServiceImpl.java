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
    private static final String DEFAULT_AVATAR_PATH = "/data/images/c21f969b5f03d33d43e04f8f136e7682.png";
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(admin -> modelMapper.map(admin, AdminDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public AdminDTO getAdminById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Admin> optionalAdmin = adminRepository.findById(uuid);
        return optionalAdmin.map(admin -> modelMapper.map(admin, AdminDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Admin"));
    }

    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        Role role = roleService.getRoleByName("Admin");
        Admin admin = modelMapper.map(adminDTO, Admin.class);
        if(!StringUtils.hasText(adminDTO.getAvatar())){
            adminDTO.setAvatar(DEFAULT_AVATAR_PATH);
        }
        if (adminRepository.existsByEmail(adminDTO.getEmail().trim())) {
            throw new DuplicateResourceException("Email không hợp lệ");
        }
        if(adminRepository.existsByPhoneNumber(adminDTO.getPhoneNumber().trim())){
            throw new DuplicateResourceException("Số điện thoại không hợp lệ");
        }
        admin.setAvatar(adminDTO.getAvatar());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        admin.setRole(role);
        admin.setStatus(UserStatus.ACTIVE);
        return modelMapper.map(adminRepository.save(admin), AdminDTO.class);
    }


    @Override
    public AdminDTO updateAdmin(AdminDTO adminDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Admin existingAdmin = adminRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Admin"));
        modelMapper.map(adminDTO, existingAdmin);
        return modelMapper.map(adminRepository.save(existingAdmin), AdminDTO.class);
    }

    @Override
    public void deleteAdmin(String id) {
        UUID uuid = UUID.fromString(id);
        Admin existingAdmin = adminRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Admin"));
        adminRepository.delete(existingAdmin);
    }
}
