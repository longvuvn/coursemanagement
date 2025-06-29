package com.example.coursemanagement.services;

import com.example.coursemanagement.models.Admin;
import com.example.coursemanagement.models.dto.AdminDTO;

import java.util.List;

public interface AdminService {
    List<AdminDTO> getAllAdmins();
    AdminDTO getAdminById(String id);
    AdminDTO createAdmin(AdminDTO adminDTO);
    AdminDTO updateAdmin(AdminDTO adminDTO, String id);
    void deleteAdmin(String id);
}
