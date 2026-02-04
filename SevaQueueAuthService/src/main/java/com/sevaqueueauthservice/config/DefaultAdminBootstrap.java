package com.sevaqueueauthservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sevaqueueauthservice.entity.Role;
import com.sevaqueueauthservice.entity.User;
import com.sevaqueueauthservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultAdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${sevaqueue.bootstrap.admin.email:admin@sevaqueue.com}")
    private String adminEmail;

    @Value("${sevaqueue.bootstrap.admin.password:Admin@123}")
    private String adminPassword;

    @Value("${sevaqueue.bootstrap.admin.name:SevaQueue Admin}")
    private String adminName;

    @Value("${sevaqueue.bootstrap.admin.mobile:9999999999}")
    private String adminMobile;

    @Override
    public void run(String... args) {
        boolean anyAdminExists = !userRepository.findByRole(Role.ADMIN).isEmpty();
        if (anyAdminExists) {
            return;
        }

        if (userRepository.existsByEmail(adminEmail)) {
            log.warn("Bootstrap admin email already exists but no ADMIN user found. Skipping bootstrap for email={}.",
                    adminEmail);
            return;
        }

        User admin = new User();
        admin.setName(adminName);
        admin.setEmail(adminEmail);
        admin.setMobile(adminMobile);
        admin.setRole(Role.ADMIN);
        admin.setPassword(passwordEncoder.encode(adminPassword));

        userRepository.save(admin);
        log.warn("Bootstrapped default ADMIN user: email='{}' password='{}'. Change it after first login.", adminEmail,
                adminPassword);
    }
}

