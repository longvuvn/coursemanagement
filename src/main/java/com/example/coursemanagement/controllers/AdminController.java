package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.AdminDTO;
import com.example.coursemanagement.services.AdminService;
import com.example.coursemanagement.services.exceptions.errors.BadRequestException;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    //lấy tất cả Admin
    @GetMapping()
    public ResponseEntity<APIResponse<List<AdminDTO>>> getAll() {
        try{
            List<AdminDTO> admins = adminService.getAllAdmins();
            APIResponse<List<AdminDTO>> response = new APIResponse<>(
                    "success",
                    "Admins retrieved successfully",
                    admins,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }catch(ResourceNotFoundException ex){
            APIResponse<List<AdminDTO>> response = new APIResponse<>(
                    "error",
                    "Admins retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //lấy Admin theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<AdminDTO>> getById(@PathVariable String id) {
        try{
            AdminDTO admin = adminService.getAdminById(id);
            APIResponse<AdminDTO> response = new APIResponse<>(
                    "success",
                    "Admin retrieved successfully",
                    admin,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }catch(ResourceNotFoundException ex){
            APIResponse<AdminDTO> response = new APIResponse<>(
                    "error",
                    "Admin not found",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }
    }

    //Tạo Admin
    @PostMapping()
    public ResponseEntity<APIResponse<AdminDTO>> create(@Valid @RequestBody AdminDTO adminDTO) {
        try{
            AdminDTO created = adminService.createAdmin(adminDTO);
            APIResponse<AdminDTO> response = new APIResponse<>(
                    "success",
                    "Admin created successfully",
                    created,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (BadRequestException ex){
            APIResponse<AdminDTO> response = new APIResponse<>(
                    "error",
                    "Admin created failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //chỉnh sửa Admin
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<AdminDTO>> update(@PathVariable String id, @RequestBody AdminDTO adminDTO) {
        try {
            AdminDTO updated = adminService.updateAdmin(adminDTO, id);
            APIResponse<AdminDTO> response = new APIResponse<>(
                    "success",
                    "Admin updated successfully",
                    updated,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<AdminDTO> response = new APIResponse<>(
                    "error",
                    "Admin created failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //Xóa Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try {
            adminService.deleteAdmin(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Admin deleted successfully",
                    null,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (ResourceNotFoundException ex) {
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Admin deletion failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
