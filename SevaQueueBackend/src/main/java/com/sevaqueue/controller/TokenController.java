package com.sevaqueue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.entity.Token;
import com.sevaqueue.entity.User;
import com.sevaqueue.service.TokenServiceImpl;

@RestController
public class TokenController {
	
	@Autowired
	private TokenServiceImpl tokenService;
	
	@PostMapping
	public ResponseEntity<Token> generateToken(@RequestParam Long serviceId, User user) {
		return ResponseEntity.ok(tokenService.generateToken(serviceId, user));
	}
	
	@PostMapping("/call-next")
	public ResponseEntity<Token> callNext(@RequestParam Long serviceId) {
		return ResponseEntity.ok(tokenService.callNextToken(serviceId));
	}
}
