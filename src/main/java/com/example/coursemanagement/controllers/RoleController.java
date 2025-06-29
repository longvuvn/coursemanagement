package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.RoleDTO;
import com.example.coursemanagement.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<APIResponse<List<RoleDTO>>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        APIResponse<List<RoleDTO>> response = new APIResponse<>(
                "success",
                "Roles retrieved successfully",
                roles,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<RoleDTO>> getRoleById(@PathVariable String id) {
        RoleDTO role = roleService.getRoleById(id);
        APIResponse<RoleDTO> response = new APIResponse<>(
                "success",
                "Role retrieved successfully",
                role,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<RoleDTO>> createRole(@RequestBody @Valid RoleDTO roleDTO) {
        RoleDTO created = roleService.createRole(roleDTO);
        APIResponse<RoleDTO> response = new APIResponse<>(
                "success",
                "Role created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<RoleDTO>> updateRole(@PathVariable String id, @RequestBody RoleDTO roleDTO) {
        RoleDTO updated = roleService.updateRole(roleDTO, id);
        APIResponse<RoleDTO> response = new APIResponse<>(
                "success",
                "Role updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Role deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
