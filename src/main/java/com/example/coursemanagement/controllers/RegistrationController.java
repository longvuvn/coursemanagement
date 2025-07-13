package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.RegistrationDTO;
import com.example.coursemanagement.services.RegistrationService;
import com.example.coursemanagement.services.exceptions.errors.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    // Lấy tất cả registration
    @GetMapping
    public ResponseEntity<APIResponse<List<RegistrationDTO>>> getAll() {
        try {
            List<RegistrationDTO> registrations = registrationService.getAllRegistrations();
            APIResponse<List<RegistrationDTO>> response = new APIResponse<>(
                    "success",
                    "Registrations retrieved successfully",
                    registrations,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            APIResponse<List<RegistrationDTO>> response = new APIResponse<>(
                    "error",
                    "Failed to retrieve registrations",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Lấy registration theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<RegistrationDTO>> getById(@PathVariable String id) {
        try {
            RegistrationDTO registration = registrationService.getRegistrationById(id);
            APIResponse<RegistrationDTO> response = new APIResponse<>(
                    "success",
                    "Registration retrieved successfully",
                    registration,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            APIResponse<RegistrationDTO> response = new APIResponse<>(
                    "error",
                    "Failed to retrieve registration",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Tạo registration
    @PostMapping
    public ResponseEntity<APIResponse<RegistrationDTO>> create(@Valid @RequestBody RegistrationDTO registrationDTO) {
        try {
            RegistrationDTO created = registrationService.createRegistration(registrationDTO);
            APIResponse<RegistrationDTO> response = new APIResponse<>(
                    "success",
                    "Registration created successfully",
                    created,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            APIResponse<RegistrationDTO> response = new APIResponse<>(
                    "error",
                    "Failed to create registration",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Chỉnh sửa registration
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<RegistrationDTO>> update(@PathVariable String id,
                                                               @RequestBody RegistrationDTO registrationDTO) {
        try {
            RegistrationDTO updated = registrationService.updateRegistration(registrationDTO, id);
            APIResponse<RegistrationDTO> response = new APIResponse<>(
                    "success",
                    "Registration updated successfully",
                    updated,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            APIResponse<RegistrationDTO> response = new APIResponse<>(
                    "error",
                    "Failed to update registration",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Xóa registration
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try {
            registrationService.deleteRegistration(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Registration deleted successfully",
                    null,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception ex) {
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Failed to delete registration",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

