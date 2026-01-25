package com.sevaqueue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sevaqueue.dto.QueueStatusDto;
import com.sevaqueue.entity.Token;
import com.sevaqueue.entity.TokenStatus;
import com.sevaqueue.entity.User;

@Service
public interface TokenService {

	public Token generateToken(Long serviceId, User userId);
	
	public Token callNextToken(Long serviceId);

	public List<Token> getTokenByUser(User user);

	public QueueStatusDto getQueueStatus(Long serviceId, User user);

	public Token updateStatus(Long tokenId, TokenStatus status);

	public List<Token> getTokenByService(Long serviceId);
	
	public List<Token> getTodayTokens(Long serviceId);
	
}
