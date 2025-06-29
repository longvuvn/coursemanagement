package com.example.coursemanagement.services;

import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    RoleDTO getRoleById(String id);
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO updateRole(RoleDTO roleDTO, String id);
    void deleteRole(String id);
    Role getRoleByName(String name);
}
