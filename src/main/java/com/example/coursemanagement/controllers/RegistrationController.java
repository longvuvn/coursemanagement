package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.RegistrationDTO;
import com.example.coursemanagement.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    //lấy tất cả registration
    @GetMapping
    public ResponseEntity<APIResponse<List<RegistrationDTO>>> getAllRegistrations() {
        List<RegistrationDTO> registrations = registrationService.getAllRegistrations();
        APIResponse<List<RegistrationDTO>> response = new APIResponse<>(
                "success",
                "Registrations retrieved successfully",
                registrations,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //lấy registration theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<RegistrationDTO>> getRegistrationById(@PathVariable String id) {
        RegistrationDTO registration = registrationService.getRegistrationById(id);
        APIResponse<RegistrationDTO> response = new APIResponse<>(
                "success",
                "Registration retrieved successfully",
                registration,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //tạo registration
    @PostMapping
    public ResponseEntity<APIResponse<RegistrationDTO>> createRegistration(@Valid @RequestBody RegistrationDTO registrationDTO) {
        RegistrationDTO created = registrationService.createRegistration(registrationDTO);
        APIResponse<RegistrationDTO> response = new APIResponse<>(
                "success",
                "Registration created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //chỉnh sửa registration
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<RegistrationDTO>> updateRegistration(@PathVariable String id, @RequestBody RegistrationDTO registrationDTO) {
        RegistrationDTO updated = registrationService.updateRegistration(registrationDTO, id);
        APIResponse<RegistrationDTO> response = new APIResponse<>(
                "success",
                "Registration updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //xóa registration
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteRegistration(@PathVariable String id) {
        registrationService.deleteRegistration(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Registration deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
