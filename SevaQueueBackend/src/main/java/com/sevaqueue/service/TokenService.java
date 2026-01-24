package com.sevaqueue.service;

import org.springframework.stereotype.Service;

import com.sevaqueue.entity.Token;
import com.sevaqueue.entity.User;

@Service
public interface TokenService {

	public Token generateToken(Long serviceId, User user);
	
	public Token callNextToken(Long serviceId);
	
}
