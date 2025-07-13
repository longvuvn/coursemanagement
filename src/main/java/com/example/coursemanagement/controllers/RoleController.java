package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.RoleDTO;
import com.example.coursemanagement.services.RoleService;
import com.example.coursemanagement.services.exceptions.errors.BadRequestException;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    //lấy tất cả roles
    @GetMapping
    public ResponseEntity<APIResponse<List<RoleDTO>>> getAll() {
        try{
            List<RoleDTO> roles = roleService.getAllRoles();
            APIResponse<List<RoleDTO>> response = new APIResponse<>(
                    "success",
                    "Roles retrieved successfully",
                    roles,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<RoleDTO>> response = new APIResponse<>(
                    "error",
                    "Roles retrieved failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //lấy role theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<RoleDTO>> getById(@PathVariable String id) {
        try {
            RoleDTO role = roleService.getRoleById(id);
            APIResponse<RoleDTO> response = new APIResponse<>(
                    "success",
                    "Role retrieved successfully",
                    role,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            APIResponse<RoleDTO> response = new APIResponse<>(
                    "error",
                    "Role not found",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //tạo role
    @PostMapping
    public ResponseEntity<APIResponse<RoleDTO>> create(@RequestBody @Valid RoleDTO roleDTO) {
        try {
            RoleDTO created = roleService.createRole(roleDTO);
            APIResponse<RoleDTO> response = new APIResponse<>(
                    "success",
                    "Role created successfully",
                    created,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            APIResponse<RoleDTO> response = new APIResponse<>(
                    "error",
                    "Role creation failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //chỉnh sửa role
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<RoleDTO>> update(@PathVariable String id, @RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO updated = roleService.updateRole(roleDTO, id);
            APIResponse<RoleDTO> response = new APIResponse<>(
                    "success",
                    "Role updated successfully",
                    updated,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            APIResponse<RoleDTO> response = new APIResponse<>(
                    "error",
                    "Role update failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //xóa role
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try {
            roleService.deleteRole(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Role deleted successfully",
                    null,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (ResourceNotFoundException ex) {
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Role deletion failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
