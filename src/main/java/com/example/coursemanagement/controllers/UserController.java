package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.UserDTO;
import com.example.coursemanagement.services.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //lấy tất cả users
    @GetMapping
    public ResponseEntity<APIResponse<List<UserDTO>>> getAll() {
        try{
            List<UserDTO> users = userService.getAllUsers();
            APIResponse<List<UserDTO>> response = new APIResponse<>(
                    "success",
                    "Users retrieved successfully",
                    users,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<UserDTO>> response = new APIResponse<>(
                    "error",
                    "User retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // lấy user theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserDTO>> getById(@PathVariable String id) {
        try{
            UserDTO user = userService.getUserById(id);
            APIResponse<UserDTO> response = new APIResponse<>(
                    "success",
                    "User retrieved successfully",
                    user,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<UserDTO> response = new APIResponse<>(
                    "error",
                    "User not found",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //tạo user
    @PostMapping
    public ResponseEntity<APIResponse<UserDTO>> create(@Valid @RequestBody UserDTO userDTO) {
        try{
            UserDTO created = userService.createUser(userDTO);
            APIResponse<UserDTO> response = new APIResponse<>(
                    "success",
                    "User created successfully",
                    created,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (BadRequestException ex){
            APIResponse<UserDTO> response = new APIResponse<>(
                    "error",
                    "User created failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //chỉnh sửa user
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UserDTO>> update(@PathVariable String id, @RequestBody UserDTO userDTO) {
        try{
            UserDTO updated = userService.updateUser(userDTO, id);
            APIResponse<UserDTO> response = new APIResponse<>(
                    "success",
                    "User updated successfully",
                    updated,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<UserDTO> response = new APIResponse<>(
                    "error",
                    "User updated failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //xóa user
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try{
            userService.deleteUser(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "User deleted successfully",
                    null,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "User deleted failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
