package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.AdminDTO;
import com.example.coursemanagement.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    //lấy Admin theo id
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getById(@PathVariable String id) {
        AdminDTO admin = adminService.getAdminById(id);
        return ResponseEntity.status(HttpStatus.OK).body(admin);
    }


    //chỉnh sửa Admin
    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> update(@PathVariable String id, @RequestBody AdminDTO adminDTO) {
       AdminDTO updated = adminService.updateAdmin(adminDTO, id);
       return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}
