package com.sevaqueueauthservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueueauthservice.security.JwtUtils;
import com.sevaqueueauthservice.security.UserPrincipal;
import com.sevaqueueauthservice.DTO.AuthResponseDTO;
import com.sevaqueueauthservice.DTO.LoginRequestDTO;
import com.sevaqueueauthservice.DTO.RegisterRequestDTO;
import com.sevaqueueauthservice.DTO.ResetPasswordDTO;
import com.sevaqueueauthservice.DTO.UpdateUserDTO;
import com.sevaqueueauthservice.DTO.UserResponseDTO;
import com.sevaqueueauthservice.entity.User;
import com.sevaqueueauthservice.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterRequestDTO user) {
		return ResponseEntity.ok(userService.register(user));
	}

	@PostMapping("/register-staff")
	@org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> registerStaff(@RequestBody RegisterRequestDTO user) {
		return ResponseEntity.ok(userService.registerStaff(user));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(
			@RequestBody LoginRequestDTO request) {

		Authentication auth = new UsernamePasswordAuthenticationToken(
				request.getEmail(),
				request.getPassword());

		Authentication authenticated = authenticationManager.authenticate(auth);

		UserPrincipal principal = (UserPrincipal) authenticated.getPrincipal();

		String token = jwtUtils.generateToken(principal);

		return ResponseEntity.ok(
				new AuthResponseDTO(token, "Login successful", principal.getUserId(), principal.getRole().name(),
						principal.getUsername()));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<UserResponseDTO> update(
			@PathVariable Long id,
			@RequestBody UpdateUserDTO dto) {
		return ResponseEntity.ok(userService.update(id, dto));
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		userService.delete(id);
		return "User deleted successfully";
	}

	@PostMapping("/reset-password")
	public String resetPassword(@RequestBody ResetPasswordDTO dto) {
		userService.resetPassword(dto);
		return "Password reset successful";
	}

	@org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
	@org.springframework.web.bind.annotation.GetMapping("/staff")
	public ResponseEntity<java.util.List<UserResponseDTO>> getAllStaff() {
		return ResponseEntity.ok(userService.getAllStaff());
	}

}
