package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.enums.RoleStatus;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.dto.RoleDTO;
import com.example.coursemanagement.repositories.RoleRepository;
import com.example.coursemanagement.services.RoleService;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Role> optionalRole = roleRepository.findById(uuid);
        return optionalRole.map(role -> modelMapper.map(role, RoleDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Role"));
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        role.setStatus(RoleStatus.ACTIVE);
        return modelMapper.map(roleRepository.save(role), RoleDTO.class) ;
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Role existingRole = roleRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Role"));
        existingRole.setName(roleDTO.getName());
        existingRole.setStatus(RoleStatus.valueOf(roleDTO.getStatus()));
        Role updatedRole = roleRepository.save(existingRole);
        return modelMapper.map(updatedRole, RoleDTO.class);
    }

    @Override
    public void deleteRole(String id) {
        UUID uuid = UUID.fromString(id);
        Role existingRole = roleRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This Role"));
        roleRepository.delete(existingRole);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
