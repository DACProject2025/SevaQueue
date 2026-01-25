package com.sevaqueue.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.entity.Service;
import com.sevaqueue.entity.Token;
import com.sevaqueue.entity.TokenStatus;
import com.sevaqueue.entity.User;
import com.sevaqueue.repository.ServiceRepository;
import com.sevaqueue.repository.TokenRepository;
<<<<<<< HEAD
=======
import com.sevaqueue.repository.UserRepository;
>>>>>>> 48004d787c36e980746bf36827f47403342b9434

@org.springframework.stereotype.Service
@Transactional
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private ServiceRepository serviceRepo;
    
    @Autowired
    private UserRepository userRepo;

    // ✅ CITIZEN → Generate token
    @Override
    public Token generateToken(Long serviceId, User user) {

        Service service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

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
        Service service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // get waiting tokens for this service
        List<Token> waitingTokens =
                tokenRepo.findWaitingTokensByService(serviceId);

        if (waitingTokens.isEmpty()) {
            throw new RuntimeException("No tokens in queue");
        }

        // take first token
        Token token = waitingTokens.get(0);
        token.setStatus(TokenStatus.CALLED);

        return tokenRepo.save(token);
    }

}
