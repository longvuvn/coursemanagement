package com.example.coursemanagement.controllers;


import com.example.coursemanagement.models.dto.UserDTO;
import com.example.coursemanagement.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //lấy tất cả users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    // lấy user theo id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable String id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    //tạo user
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        UserDTO created = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //chỉnh sửa user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable String id, @RequestBody UserDTO userDTO) {
        UserDTO updated = userService.updateUser(userDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //xóa user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
