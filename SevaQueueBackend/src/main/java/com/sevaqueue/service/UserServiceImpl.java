package com.sevaqueue.service;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sevaqueue.dto.RegisterRequestDTO;
import com.sevaqueue.entity.Role;
import com.sevaqueue.entity.User;
import com.sevaqueue.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public User addStaff(RegisterRequestDTO dto) {
        User user = modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.STAFF); // 🔒 enforced here
        user.setCreatedAt(LocalDate.now());
        return userRepository.save(user);
    }
}
