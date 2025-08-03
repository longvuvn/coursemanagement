package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.RegistrationDTO;
import com.example.coursemanagement.services.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
@Tag(name = "Registration Management", description = "Operations related to course registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(summary = "Get all registrations")
    @GetMapping
    public ResponseEntity<List<RegistrationDTO>> getAll() {
        List<RegistrationDTO> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.status(HttpStatus.OK).body(registrations);
    }

    @Operation(summary = "Get a registration by ID")
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> getById(@PathVariable String id) {
        RegistrationDTO registration = registrationService.getRegistrationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(registration);
    }

    @Operation(summary = "Create a new registration")
    @PostMapping
    public ResponseEntity<RegistrationDTO> create(@Valid @RequestBody RegistrationDTO registrationDTO) {
        RegistrationDTO created = registrationService.createRegistration(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update a registration")
    @PutMapping("/{id}")
    public ResponseEntity<RegistrationDTO> update(@PathVariable String id, @RequestBody RegistrationDTO registrationDTO) {
        RegistrationDTO updated = registrationService.updateRegistration(registrationDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @Operation(summary = "Delete a registration by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
