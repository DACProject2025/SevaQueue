package com.sevaqueueauthservice.service;

import com.sevaqueueauthservice.DTO.LoginRequestDTO;
import com.sevaqueueauthservice.DTO.RegisterRequestDTO;
import com.sevaqueueauthservice.DTO.ResetPasswordDTO;
import com.sevaqueueauthservice.DTO.UpdateUserDTO;
import com.sevaqueueauthservice.DTO.UserResponseDTO;
import com.sevaqueueauthservice.entity.User;

public interface UserService {

	User register(RegisterRequestDTO user);

	User registerStaff(RegisterRequestDTO user);

	String login(LoginRequestDTO dto);

	void delete(Long id);

	void resetPassword(ResetPasswordDTO dto);

	UserResponseDTO update(Long userId, UpdateUserDTO dto);

	java.util.List<UserResponseDTO> getAllStaff();
}
