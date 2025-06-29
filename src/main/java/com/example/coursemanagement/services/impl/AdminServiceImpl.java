package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.UserStatus;
import com.example.coursemanagement.models.Admin;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.dto.AdminDTO;
import com.example.coursemanagement.repositories.AdminRepository;
import com.example.coursemanagement.services.AdminService;
import com.example.coursemanagement.services.exceptions.error.DuplicateResourceException;
import com.example.coursemanagement.services.exceptions.error.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    @Override
    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(this::adminToAdminDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AdminDTO getAdminById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Admin> admin = adminRepository.findById(uuid);
        return admin.map(this::adminToAdminDTO).orElse(null);
    }

    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        Instant now = Instant.now();
        Role role = roleService.getRoleByName("Admin");
        if(adminDTO.getAvatar() == null || adminDTO.getAvatar().isEmpty()){
            adminDTO.setAvatar(DEFAULT_AVATAR_PATH);
        }
        if (adminDTO.getEmail() == null || adminDTO.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email không được để trống");
        }
        if (adminRepository.existsByEmail(adminDTO.getEmail().trim())) {
            throw new DuplicateResourceException("Email đã tồn tại");
        }
        if(adminDTO.getFullName() == null || adminDTO.getFullName().trim().isEmpty()){
            throw new ValidationException("Tên người dùng không được để trống");
        }
        if(adminRepository.existsByFullName(adminDTO.getFullName().trim())){
            throw new DuplicateResourceException("Tên người dùng đã được sử dụng");
        }
        if(adminDTO.getPhoneNumber() == null || adminDTO.getPhoneNumber().trim().isEmpty()){
            throw new ValidationException("Số điện thoại không được để trống");
        }
        if(adminRepository.existsByPhoneNumber(adminDTO.getPhoneNumber().trim())){
            throw new DuplicateResourceException("Số điện thoại đã được sử dụng");
        }
        if(adminDTO.getPassword() == null || adminDTO.getPassword().trim().isEmpty()){
            throw new ValidationException("Vui lòng nhập mật khẩu");
        }
        admin.setFullName(adminDTO.getFullName());
        admin.setEmail(adminDTO.getEmail());
        admin.setPhoneNumber(adminDTO.getPhoneNumber());
        admin.setPassword(adminDTO.getPassword());
        admin.setAvatar(adminDTO.getAvatar());
        admin.setRole(role);
        admin.setStatus(UserStatus.ACTIVE);
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        admin.setDepartment(adminDTO.getDepartment());
        return adminToAdminDTO(adminRepository.save(admin));
    }

    @Override
    public AdminDTO updateAdmin(AdminDTO adminDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Admin existingAdmin = adminRepository.findById(uuid).orElse(null);
        existingAdmin.setFullName(adminDTO.getFullName());
        existingAdmin.setEmail(adminDTO.getEmail());
        existingAdmin.setPhoneNumber(adminDTO.getPhoneNumber());
        existingAdmin.setAvatar(adminDTO.getAvatar());
        existingAdmin.setPassword(adminDTO.getPassword());
        existingAdmin.setDepartment(adminDTO.getDepartment());
        existingAdmin.setStatus(UserStatus.valueOf(adminDTO.getStatus()));
        existingAdmin.setUpdatedAt(Instant.now());
        return adminToAdminDTO(adminRepository.save(existingAdmin));
    }

    @Override
    public void deleteAdmin(String id) {
        UUID uuid = UUID.fromString(id);
        Admin existingAdmin = adminRepository.findById(uuid).orElse(null);
        adminRepository.delete(existingAdmin);
    }

    public AdminDTO adminToAdminDTO(Admin admin){
        AdminDTO dto = new AdminDTO();
        dto.setId(String.valueOf(admin.getId()));
        dto.setFullName(admin.getFullName());
        dto.setEmail(admin.getEmail());
        dto.setPhoneNumber(admin.getPhoneNumber());
        dto.setPassword(admin.getPassword());
        dto.setAvatar(admin.getAvatar());
        dto.setRoleName(admin.getRole().getName());
        dto.setStatus(admin.getStatus().name());
        dto.setCreatedAt(String.valueOf(admin.getCreatedAt()));
        dto.setUpdatedAt(String.valueOf(admin.getUpdatedAt()));
        dto.setDepartment(admin.getDepartment());
        return dto;
    }
}
