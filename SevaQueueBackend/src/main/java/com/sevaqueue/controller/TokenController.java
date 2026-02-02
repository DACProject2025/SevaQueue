package com.sevaqueue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.dto.TokenResponseDto;
import com.sevaqueue.entity.TokenStatus;
import com.sevaqueue.entity.User;
import com.sevaqueue.security.UserPrincipal;
import com.sevaqueue.service.TokenService;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/generate-token")
	public ResponseEntity<TokenResponseDto> generateToken(
	        @RequestParam Long serviceId,
	        @AuthenticationPrincipal UserPrincipal principal) {

	    return ResponseEntity.ok(
	            tokenService.generateToken(serviceId, principal)
	    );
	}

	
	@PostMapping("/call-next")
	public ResponseEntity<TokenResponseDto> callNext(@RequestParam Long serviceId, @RequestParam Long counterId) {
		return ResponseEntity.ok(tokenService.callNextToken(serviceId, counterId));
	}
	
	@GetMapping("/my-tokens")
	public ResponseEntity<List<TokenResponseDto>> getMyTokens(User user) {
		return ResponseEntity.ok(tokenService.getTokenByUser(user));
	}
	
	@GetMapping("/queue/{serviceId}") 
	public ResponseEntity<?> getQueueStatus(
			@PathVariable Long serviceId,
			User user) {
		return ResponseEntity.ok(tokenService.getQueueStatus(serviceId, user));
	}
	
	@PutMapping("/{tokenId}/status")
	public ResponseEntity<TokenResponseDto> updateStatus(
			@PathVariable Long tokenId,
			@RequestParam TokenStatus status) {
		
		return ResponseEntity.ok(tokenService.updateStatus(tokenId, status));
		
	}
	
	@GetMapping("/service/{serviceId}")
	public ResponseEntity<List<TokenResponseDto>> getTokensByService(@PathVariable Long serviceId) {
		return ResponseEntity.ok(tokenService.getTokenByService(serviceId));
	}
	
	@GetMapping("/service/{serviceId}/today")
	public ResponseEntity<List<TokenResponseDto>> getTodayTokens(@PathVariable Long serviceId) {
		return ResponseEntity.ok(tokenService.getTodayTokens(serviceId));
	}
	
}
