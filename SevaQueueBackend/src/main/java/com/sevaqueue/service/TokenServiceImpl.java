package com.sevaqueue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.dto.QueueStatusDto;
import com.sevaqueue.entity.Counter;
import com.sevaqueue.entity.CounterStatus;
import com.sevaqueue.entity.OfficeService;
import com.sevaqueue.entity.Token;
import com.sevaqueue.entity.TokenStatus;
import com.sevaqueue.entity.User;
import com.sevaqueue.exception.QueueEmptyException;
import com.sevaqueue.exception.ResourceNotFoundException;
import com.sevaqueue.repository.CounterRepository;
import com.sevaqueue.repository.OfficeServiceRepository;
import com.sevaqueue.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepo;
    
    @Autowired
    private CounterRepository counterRepo;

    @Autowired
    private OfficeServiceRepository serviceRepo;

    // ✅ CITIZEN → Generate token
    @Override
    @Transactional
    public Token generateToken(Long serviceId, User user) {

        OfficeService service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        if(!service.isActive()) {
        	throw new IllegalStateException("Service is inactive!");
        }
        
        int lastTokenNumber = tokenRepo.findLastTokenNumber(serviceId);

        Token token = new Token();
        token.setTokenNumber(lastTokenNumber + 1);
        token.setStatus(TokenStatus.WAITING);
        token.setService(service);
        token.setUser(user);

        return tokenRepo.save(token);
    }

    @Override
    @Transactional
    public Token callNextToken(Long serviceId, Long counterId) {

    	Counter counter = counterRepo.findById(counterId)
    			.orElseThrow(() -> new ResourceNotFoundException("Counter not found!"));
    	
    	if(counter.getStatus() == CounterStatus.CLOSED) {
    		throw new IllegalStateException("Counter is Closed!");
    	}
    	
        // check service exists
        OfficeService service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        // get waiting tokens for this service
        List<Token> waitingTokens =
                tokenRepo.findWaitingTokensByService(serviceId);

        if (waitingTokens.isEmpty()) {
            throw new QueueEmptyException("No tokens in queue");
        }

        // take first token
        Token token = waitingTokens.get(0);
        token.setStatus(TokenStatus.CALLED);

        return tokenRepo.save(token);
    }

    @Override
	public List<Token> getTokenByUser(User user) {
		
		return tokenRepo.findByUser(user);
	}

	@Override
	public QueueStatusDto getQueueStatus(Long serviceId, User user) {

		List<Token> waiting = tokenRepo.findWaitingTokensByService(serviceId);
		
		int current = waiting.isEmpty() ? 0 : waiting.get(0).getTokenNumber();
		int myToken = tokenRepo.findUserTokenNumber(serviceId, user.getId());
		
		return new QueueStatusDto(current, myToken, Math.max(0, myToken - current));
	}

	@Override
	@Transactional
	public Token updateStatus(Long tokenId, TokenStatus status) {
		
		Token token = tokenRepo.findById(tokenId)
				.orElseThrow(() -> new ResourceNotFoundException("Token not found!"));
		token.setStatus(status);
		return tokenRepo.save(token);
		
	}

	@Override
	public List<Token> getTokenByService(Long serviceId) {
		
		return tokenRepo.findByServiceServiceIdOrderByCreatedAt(serviceId);
	}

	@Override
	public List<Token> getTodayTokens(Long serviceId) {
		
		return tokenRepo.findTodayTokens(serviceId);
	}

}
