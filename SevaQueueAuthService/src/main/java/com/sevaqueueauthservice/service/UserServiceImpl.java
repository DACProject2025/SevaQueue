package com.sevaqueueauthservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sevaqueueauthservice.DTO.LoginRequestDTO;
import com.sevaqueueauthservice.DTO.RegisterRequestDTO;
import com.sevaqueueauthservice.DTO.ResetPasswordDTO;
import com.sevaqueueauthservice.DTO.UpdateUserDTO;
import com.sevaqueueauthservice.DTO.UserResponseDTO;
import com.sevaqueueauthservice.customexception.EmailAlreadyExistsException;
import com.sevaqueueauthservice.customexception.InvalidCredentialsException;
import com.sevaqueueauthservice.customexception.UserNotFoundException;
import com.sevaqueueauthservice.entity.Role;
import com.sevaqueueauthservice.entity.User;
import com.sevaqueueauthservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User register(RegisterRequestDTO dto) {

		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new EmailAlreadyExistsException("Email already registered");
		}

		// 1. Map DTO → Entity
		User user = modelMapper.map(dto, User.class);

		// 2. Encode password
		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		// 3. Set default role
		if (user.getRole() == null) {
			user.setRole(Role.CITIZEN);
		}

		// 4. Save entity
		return userRepository.save(user);
	}

	@Override
	public User registerStaff(RegisterRequestDTO dto) {

		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new EmailAlreadyExistsException("Email already registered");
		}

		User user = modelMapper.map(dto, User.class);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(Role.STAFF);

		return userRepository.save(user);
	}

	@Override
	public String login(LoginRequestDTO dto) {

		User user = userRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsException("Invalid email or password");
		}

		return "login successfully";
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public void resetPassword(ResetPasswordDTO dto) {

		User user = userRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		userRepository.save(user);
	}

	@Override
	public UserResponseDTO update(Long userId, UpdateUserDTO dto) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		user.setName(dto.getName());
		user.setMobile(dto.getMobile());
		user.setEmail(dto.getEmail());

		User savedUser = userRepository.save(user);

		return modelMapper.map(savedUser, UserResponseDTO.class);
	}

	@Override
	public java.util.List<UserResponseDTO> getAllStaff() {
		return userRepository.findByRole(Role.STAFF)
				.stream()
				.map(user -> modelMapper.map(user, UserResponseDTO.class))
				.toList();
	}

}
