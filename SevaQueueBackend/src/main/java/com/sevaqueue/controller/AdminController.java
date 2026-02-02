package com.sevaqueue.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.dto.RegisterRequestDTO;
import com.sevaqueue.entity.User;
import com.sevaqueue.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-staff")
    public ResponseEntity<User> addStaff(@RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(userService.addStaff(dto));
    }
}
