package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.models.dto.RegistrationDTO;

import java.util.List;

public interface RegistrationService {
    List<RegistrationDTO> getAllRegistrations();
    RegistrationDTO getRegistrationById(String id);
    RegistrationDTO createRegistration(RegistrationDTO registrationDTO);
    RegistrationDTO updateRegistration(RegistrationDTO registrationDTO, String id);
    void deleteRegistration(String id);
}
