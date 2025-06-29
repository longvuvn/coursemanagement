package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.RoleStatus;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.dto.RoleDTO;
import com.example.coursemanagement.repositories.RoleRepository;
import com.example.coursemanagement.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(this::roleToRoleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Role> role = roleRepository.findById(uuid);
        return role.map(this::roleToRoleDTO).orElse(null);
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setStatus(RoleStatus.ACTIVE);
        return roleToRoleDTO(roleRepository.save(role));
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Role existingRole = roleRepository.findById(uuid).orElse(null);
        existingRole.setName(roleDTO.getName());
        existingRole.setStatus(RoleStatus.valueOf(roleDTO.getStatus()));
        Role updatedRole = roleRepository.save(existingRole);
        return roleToRoleDTO(updatedRole);
    }

    @Override
    public void deleteRole(String id) {
        UUID uuid = UUID.fromString(id);
        Role existingRole = roleRepository.findById(uuid).orElse(null);
        roleRepository.delete(existingRole);
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = roleRepository.findByName(name);
        return role;
    }

    public RoleDTO roleToRoleDTO(Role role){
        RoleDTO dto = new RoleDTO();
        dto.setId(String.valueOf(role.getId()));
        dto.setName(role.getName());
        dto.setStatus(role.getStatus().name());
        return dto;
    }
}
