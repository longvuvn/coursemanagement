package com.example.coursemanagement.config;

import com.example.coursemanagement.enums.UserStatus;
import com.example.coursemanagement.models.Admin;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.repositories.AdminRepository;
import com.example.coursemanagement.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if(adminRepository.count() == 0 ){
            Role role = roleRepository.findByName("Admin");
            Admin admin = new Admin();
            admin.setFullName("ADMIN");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setPhoneNumber("0987654322");
            admin.setDepartment("Manager");
            admin.setStatus(UserStatus.ACTIVE);
            admin.setAvatar("/data/images/c21f969b5f03d33d43e04f8f136e7682.png");
            admin.setRole(role);
            adminRepository.save(admin);
        }
        System.out.println(">>> Saved admin");
    }
}
