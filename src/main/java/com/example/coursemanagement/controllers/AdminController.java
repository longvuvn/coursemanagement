package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.AdminDTO;
import com.example.coursemanagement.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    //lấy tất cả Admin
    @GetMapping()
    public ResponseEntity<APIResponse<List<AdminDTO>>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        APIResponse<List<AdminDTO>> response = new APIResponse<>(
                "success",
                "Admins retrieved successfully",
                admins,
                null,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    //lấy Admin theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<AdminDTO>> getAdminById(@PathVariable String id) {
        AdminDTO admin = adminService.getAdminById(id);
        APIResponse<AdminDTO> response = new APIResponse<>(
                "success",
                "Admin retrieved successfully",
                admin,
                null,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    //Tạo Admin
    @PostMapping()
    public ResponseEntity<APIResponse<AdminDTO>> createAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        AdminDTO created = adminService.createAdmin(adminDTO);
        APIResponse<AdminDTO> response = new APIResponse<>(
                "success",
                "Admin created successfully",
                created,
                null,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //chỉnh sửa Admin
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<AdminDTO>> updateUser(@PathVariable String id, @RequestBody AdminDTO adminDTO) {
        AdminDTO updated = adminService.updateAdmin(adminDTO, id);
        APIResponse<AdminDTO> response = new APIResponse<>(
                "success",
                "Admin updated successfully",
                updated,
                null,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    //Xóa Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteUser(@PathVariable String id) {
        adminService.deleteAdmin(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Admin deleted successfully",
                null,
                null,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
