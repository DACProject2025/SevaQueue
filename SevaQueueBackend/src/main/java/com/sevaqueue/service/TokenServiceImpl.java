package com.sevaqueue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.entity.Service;
import com.sevaqueue.entity.Token;
import com.sevaqueue.entity.TokenStatus;
import com.sevaqueue.entity.User;
import com.sevaqueue.repository.ServiceRepository;
import com.sevaqueue.repository.TokenRepository;

@org.springframework.stereotype.Service
@Transactional
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	private TokenRepository tokenRepo;
	
	@Autowired
	private ServiceRepository serviceRepo;
	

	@Override
	public Token generateToken(Long serviceId, User user) {
		
		Service service = serviceRepo.findById(serviceId)
				.orElseThrow(() -> new RuntimeException("Service not found!"));
		
		int lastToken = tokenRepo.findLastTokenNumber(serviceId);
		
		Token token = new Token();
		token.setTokenNumber(lastToken + 1);
		token.setStatus(TokenStatus.WAITING);
		token.setService(service);
		token.setUser(user);
		return tokenRepo.save(token);
		
	}
	
	@Override
	public Token callNextToken(Long serviceId) {
		
		List<Token> waitingTokens = tokenRepo.findWaitingTokens(serviceId);
		
		if(waitingTokens.isEmpty()) {
			throw new RuntimeException("No tokens in queue!");
		}
		 
		Token token = waitingTokens.get(0);
		token.setStatus(TokenStatus.CALLED);
		
		return token;
		
	}

}
