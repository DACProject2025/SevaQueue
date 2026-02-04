package com.sevaqueue.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
import com.sevaqueue.repository.UserRepository;
import com.sevaqueue.security.UserPrincipal;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private CounterRepository counterRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private OfficeServiceRepository serviceRepo;

    @Autowired
    private SmsService smsService;

    private TokenResponseDto toTokenResponseDto(Token token) {
        if (token == null) {
            return null;
        }

        TokenResponseDto dto = new TokenResponseDto();
        dto.setTokenId(token.getTokenId());
        dto.setTokenNumber(token.getTokenNumber());
        dto.setStatus(token.getStatus() != null ? token.getStatus().name() : null);
        dto.setCreatedAt(token.getCreatedAt());

        OfficeService service = token.getService();
        if (service != null) {
            dto.setServiceName(service.getServiceName());
            if (service.getOffice() != null) {
                dto.setOfficeName(service.getOffice().getOfficeName());
            }
        }

        return dto;
    }

    @Override
    @Transactional
    public TokenResponseDto generateToken(Long serviceId, UserPrincipal principal) {

        // Get logged-in user from JWT principal
        User user = userRepo.findById(principal.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        LocalDate today = LocalDate.now();
        DayOfWeek day = today.getDayOfWeek();

        if (day == DayOfWeek.SUNDAY) {
            throw new IllegalStateException("Office is closed on Sunday!");
        }

        OfficeService service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        if (!service.isActive()) {
            throw new IllegalStateException("Service is inactive!");
        }

        LocalTime now = LocalTime.now();

        if (now.isBefore(service.getOffice().getOpenTime()) ||
                now.isAfter(service.getOffice().getCloseTime())) {
            throw new IllegalStateException("Office is currently closed!");
        }

        // ✅ Check if user already has an active token for this service
        List<Token> userActiveTokens = tokenRepo.findByUser(user).stream()
                .filter(t -> t.getService().getServiceId().equals(serviceId))
                .filter(t -> t.getStatus() == TokenStatus.WAITING || t.getStatus() == TokenStatus.CALLED)
                .toList();

        if (!userActiveTokens.isEmpty()) {
            throw new IllegalStateException(
                    "You already have an active token for this service! Please wait for it to be completed.");
        }

        // ✅ Check if at least one counter is OPEN for this service
        List<Counter> counters = counterRepo.findByServiceServiceId(serviceId);
        boolean hasOpenCounter = counters.stream()
                .anyMatch(c -> c.getStatus() == CounterStatus.OPEN);

        if (!hasOpenCounter) {
            throw new IllegalStateException("No counters are currently open for this service. Please try again later.");
        }

        int todayTokenCount = tokenRepo.countTodayTokens(service.getServiceId());

        if (todayTokenCount >= service.getMaxTokensPerDay()) {
            throw new IllegalStateException("Token limit reached for today!");
        }

        int lastTokenNumber = tokenRepo.findLastTokenNumber(serviceId);

        Token token = new Token();
        token.setTokenNumber(lastTokenNumber + 1);
        token.setStatus(TokenStatus.WAITING);
        token.setService(service);
        token.setUser(user);

        Token savedToken = tokenRepo.save(token);

        // SMS Notification on Generation
        smsService.sendSms(user.getMobile(),
                "Hello " + user.getName() + ", your token #" + savedToken.getTokenNumber() +
                        " for " + service.getServiceName()
                        + " has been generated successfully. Please wait for your turn.");

        return toTokenResponseDto(savedToken);
    }

    @Override
    @Transactional
    public TokenResponseDto callNextToken(Long serviceId, Long counterId) {

        Counter counter = counterRepo.findById(counterId)
                .orElseThrow(() -> new ResourceNotFoundException("Counter not found!"));

        if (counter.getStatus() == CounterStatus.CLOSED) {
            throw new IllegalStateException("Counter is Closed!");
        }

        // check service exists
        OfficeService service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        // get waiting tokens for this service
        List<Token> waitingTokens = tokenRepo.findWaitingTokensByService(serviceId);

        if (waitingTokens.isEmpty()) {
            throw new QueueEmptyException("No tokens in queue");
        }

        // take first token
        Token token = waitingTokens.get(0);
        token.setStatus(TokenStatus.CALLED);

        Token savedToken = tokenRepo.save(token);

        // SMS Notification when token is called
        User tokenUser = savedToken.getUser();
        OfficeService tokenService = savedToken.getService();
        smsService.sendSms(tokenUser.getMobile(),
                "Hello " + tokenUser.getName() + ", your token #" + savedToken.getTokenNumber() +
                        " for " + tokenService.getServiceName() + " is now CALLED. Please proceed to the counter.");

        return toTokenResponseDto(savedToken);
    }

    @Override
    public List<TokenResponseDto> getTokenByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Token> tokens = tokenRepo.findByUser(user);

        return tokens.stream()
                .map(this::toTokenResponseDto)
                .toList();
    }

    @Override
    public QueueStatusDto getQueueStatusByToken(Long tokenId) {
        Token userToken = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        Long serviceId = userToken.getService().getServiceId();

        // Get currently serving (latest CALLED)
        List<Token> called = tokenRepo.findCalledTokensByService(serviceId);
        String currentServing = called.isEmpty() ? "None" : String.valueOf(called.get(0).getTokenNumber());

        // Count tokens ahead (WAITING and created before userToken)
        int ahead = 0;
        if (userToken.getStatus() == com.sevaqueue.entity.TokenStatus.WAITING) {
            List<Token> waiting = tokenRepo.findWaitingTokensByService(serviceId);
            for (Token t : waiting) {
                if (t.getTokenId().equals(userToken.getTokenId()))
                    break;
                ahead++;
            }
        }

        boolean isTurn = userToken.getStatus() == com.sevaqueue.entity.TokenStatus.CALLED;

        // Get average service time from the service
        int avgServiceTime = userToken.getService().getAvgServiceTime();

        return new QueueStatusDto(currentServing, ahead, String.valueOf(userToken.getTokenNumber()),
                userToken.getStatus(), isTurn, avgServiceTime);
    }

    @Override
    @Transactional
    public TokenResponseDto updateStatus(Long tokenId, TokenStatus status) {

        Token token = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found!"));
        token.setStatus(status);
        Token savedToken = tokenRepo.save(token);
        return toTokenResponseDto(savedToken);

    }

    @Override
    public List<TokenResponseDto> getTokenByService(Long serviceId) {

        return tokenRepo.findByServiceServiceIdOrderByCreatedAtDesc(serviceId)
                .stream()
                .map(this::toTokenResponseDto)
                .toList();
    }

    @Override
    public List<TokenResponseDto> getTodayTokens(Long serviceId) {

        return tokenRepo.findTodayTokens(serviceId)
                .stream()
                .map(this::toTokenResponseDto)
                .toList();
    }

}
