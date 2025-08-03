package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.AdminDTO;
import com.example.coursemanagement.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
@Tag(name = "Admin Management", description = "Manage administrator information")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Get admin by ID", description = "Returns detailed information of an administrator by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getById(
            @Parameter(description = "Administrator ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id) {
        AdminDTO admin = adminService.getAdminById(id);
        return ResponseEntity.status(HttpStatus.OK).body(admin);
    }

    @Operation(summary = "Update admin information", description = "Updates the information of an administrator by ID")
    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> update(
            @Parameter(description = "ID of the administrator to be updated", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id,

            @RequestBody
            @Parameter(description = "New administrator information") AdminDTO adminDTO) {

        AdminDTO updated = adminService.updateAdmin(adminDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}
