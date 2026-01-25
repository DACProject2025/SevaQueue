package com.sevaqueue.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.entity.OfficeService;
import com.sevaqueue.entity.Token;
import com.sevaqueue.entity.TokenStatus;
import com.sevaqueue.entity.User;
import com.sevaqueue.exception.QueueEmptyException;
import com.sevaqueue.exception.ResourceNotFoundException;
import com.sevaqueue.repository.OfficeServiceRepository;
import com.sevaqueue.repository.TokenRepository;
import com.sevaqueue.repository.UserRepository;

@org.springframework.stereotype.Service
@Transactional
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private OfficeServiceRepository serviceRepo;
    
    @Autowired
    private UserRepository userRepo;

    // ✅ CITIZEN → Generate token
    @Override
    public Token generateToken(Long serviceId, User user) {

        OfficeService service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        int lastTokenNumber = tokenRepo.findLastTokenNumber(serviceId);

        Token token = new Token();
        token.setTokenNumber(lastTokenNumber + 1);
        token.setStatus(TokenStatus.WAITING);
        token.setCreatedAt(LocalDateTime.now());
        token.setService(service);
        token.setUser(user);
        token.setUser(user);


        return tokenRepo.save(token);
    }

    @Override
    public Token callNextToken(Long serviceId) {

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

}
