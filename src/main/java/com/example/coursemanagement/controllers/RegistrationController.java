package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.RegistrationDTO;
import com.example.coursemanagement.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    // Lấy tất cả registration
    @GetMapping
    public ResponseEntity<List<RegistrationDTO>> getAll() {
        List<RegistrationDTO> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.status(HttpStatus.OK).body(registrations);
    }

    // Lấy registration theo id
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> getById(@PathVariable String id) {
        RegistrationDTO registration = registrationService.getRegistrationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(registration);
    }

    // Tạo registration
    @PostMapping
    public ResponseEntity<RegistrationDTO> create(@Valid @RequestBody RegistrationDTO registrationDTO) {
        RegistrationDTO created = registrationService.createRegistration(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Chỉnh sửa registration
    @PutMapping("/{id}")
    public ResponseEntity<RegistrationDTO> update(@PathVariable String id, @RequestBody RegistrationDTO registrationDTO) {
        RegistrationDTO updated = registrationService.updateRegistration(registrationDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // Xóa registration
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}


