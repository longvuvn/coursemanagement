package com.example.coursemanagement.controllers;


import com.example.coursemanagement.models.dto.RoleDTO;
import com.example.coursemanagement.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;




@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    //lấy tất cả roles
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAll() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    //lấy role theo id
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById(@PathVariable String id) {
        RoleDTO role = roleService.getRoleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    //tạo role
    @PostMapping
    public ResponseEntity<RoleDTO> create(@RequestBody @Valid RoleDTO roleDTO) {
        RoleDTO created = roleService.createRole(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //chỉnh sửa role
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(@PathVariable String id, @RequestBody RoleDTO roleDTO) {
        RoleDTO updated = roleService.updateRole(roleDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //xóa role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
