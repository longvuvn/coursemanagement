package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.UserDTO;
import com.example.coursemanagement.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<APIResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        APIResponse<List<UserDTO>> response = new APIResponse<>(
                "success",
                "Users retrieved successfully",
                users,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserDTO>> getUserById(@PathVariable String id) {
        UserDTO user = userService.getUserById(id);
        APIResponse<UserDTO> response = new APIResponse<>(
                "success",
                "User retrieved successfully",
                user,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO created = userService.CreateUser(userDTO);
        APIResponse<UserDTO> response = new APIResponse<>(
                "success",
                "User created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UserDTO>> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        UserDTO updated = userService.UpdateUser(userDTO, id);
        APIResponse<UserDTO> response = new APIResponse<>(
                "success",
                "User updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteUser(@PathVariable String id) {
        userService.DeleteUser(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "User deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
