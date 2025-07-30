package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.AdminDTO;

public interface AdminService {
    AdminDTO getAdminById(String id);
    AdminDTO updateAdmin(AdminDTO adminDTO, String id);
}
