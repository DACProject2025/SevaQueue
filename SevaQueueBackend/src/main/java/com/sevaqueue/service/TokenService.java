package com.sevaqueue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sevaqueue.dto.QueueStatusDto;
import com.sevaqueue.dto.TokenResponseDto;
import com.sevaqueue.entity.TokenStatus;
import com.sevaqueue.entity.User;
import com.sevaqueue.security.UserPrincipal;

@Service
public interface TokenService {

	public TokenResponseDto generateToken(Long serviceId, UserPrincipal principal);
	
	public TokenResponseDto callNextToken(Long serviceId,  Long counterId);

	public List<TokenResponseDto> getTokenByUser(User user);

	public QueueStatusDto getQueueStatus(Long serviceId, User user);

	public TokenResponseDto updateStatus(Long tokenId, TokenStatus status);

	public List<TokenResponseDto> getTokenByService(Long serviceId);
	
	public List<TokenResponseDto> getTodayTokens(Long serviceId);
	
}
