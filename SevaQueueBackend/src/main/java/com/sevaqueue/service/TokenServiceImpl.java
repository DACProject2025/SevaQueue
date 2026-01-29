package com.sevaqueue.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.dto.QueueStatusDto;
import com.sevaqueue.dto.TokenResponseDto;
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
    
    private ModelMapper modelMapper;
    
    public TokenServiceImpl() {
    	
    	modelMapper = new ModelMapper();
    	
    }

    // CITIZEN → Generate token
    @Override
    @Transactional
    public TokenResponseDto generateToken(Long serviceId, User user) {
    	
    	LocalDate today = LocalDate.now();
    	DayOfWeek day = today.getDayOfWeek();
    	
    	if(day == DayOfWeek.SUNDAY) {
    		throw new IllegalStateException("Office is closed on Sunday!");
    	}
    	
    	OfficeService service = serviceRepo.findById(serviceId)
    			.orElseThrow(() -> new ResourceNotFoundException("Service not found"));
    	
    	if(!service.isActive()) {
    		throw new IllegalStateException("Service is inactive!");
    	}
    	
    	LocalTime now = LocalTime.now();
    	
    	if(now.isBefore(service.getOffice().getOpenTime()) || now.isAfter(service.getOffice().getCloseTime())) {
    		throw new IllegalStateException("Office is currently closed!");
    	}
    	
    	int todayTokenCount = tokenRepo.countTodayTokens(service.getServiceId());
    	
    	if(todayTokenCount >= service.getMaxTokensPerDay()) {
    		throw new IllegalStateException("Token limit reached for today!");
    	}
        
        int lastTokenNumber = tokenRepo.findLastTokenNumber(serviceId);

        Token token = new Token();
        token.setTokenNumber(lastTokenNumber + 1);
        token.setStatus(TokenStatus.WAITING);
        token.setService(service);
        token.setUser(user);

        return modelMapper.map(tokenRepo.save(token), TokenResponseDto.class);
    }

    @Override
    @Transactional
    public TokenResponseDto callNextToken(Long serviceId, Long counterId) {

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

        return modelMapper.map(tokenRepo.save(token), TokenResponseDto.class);
    }

    @Override
	public List<TokenResponseDto> getTokenByUser(User user) {
		
		return tokenRepo.findByUser(user)
				.stream()
				.map(token -> modelMapper.map(token, TokenResponseDto.class))
				.toList();
	}

	@Override
	public QueueStatusDto getQueueStatus(Long serviceId, User user) {

		OfficeService service = serviceRepo.findById(serviceId)
				.orElseThrow(() -> new ResourceNotFoundException("Service not found!"));
		List<Token> waiting = tokenRepo.findWaitingTokensByService(serviceId);
		int current = waiting.isEmpty() ? 0 : waiting.get(0).getTokenNumber();
		int myToken = tokenRepo.findUserTokenNumber(serviceId, user.getId());
		int tokensAhead = Math.max(0,  myToken - current);
		int avgTime = service.getAvgServiceTime();
		
		return new QueueStatusDto(current, myToken, tokensAhead, avgTime);
	}

	@Override
	@Transactional
	public TokenResponseDto updateStatus(Long tokenId, TokenStatus status) {
		
		Token token = tokenRepo.findById(tokenId)
				.orElseThrow(() -> new ResourceNotFoundException("Token not found!"));
		token.setStatus(status);
		return modelMapper.map(tokenRepo.save(token), TokenResponseDto.class);
		
	}

	@Override
	public List<TokenResponseDto> getTokenByService(Long serviceId) {
		
		return tokenRepo.findByServiceServiceIdOrderByCreatedAt(serviceId)
				.stream()
				.map(token -> modelMapper.map(token, TokenResponseDto.class))
				.toList();
	}

	@Override
	public List<TokenResponseDto> getTodayTokens(Long serviceId) {
		
		return tokenRepo.findTodayTokens(serviceId)
				.stream()
				.map(token -> modelMapper.map(token, TokenResponseDto.class))
				.toList();
	}

}
