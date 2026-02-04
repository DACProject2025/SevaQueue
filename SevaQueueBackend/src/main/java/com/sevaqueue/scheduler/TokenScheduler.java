package com.sevaqueue.scheduler;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.entity.Token;
import com.sevaqueue.entity.TokenStatus;
import com.sevaqueue.repository.TokenRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenScheduler {

    @Autowired
    private TokenRepository tokenRepo;

    /**
     * Mark tokens from previous days as EXPIRED if they are still WAITING or
     * CALLED.
     * Runs every day at midnight (00:00:00).
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void expireOldTokens() {
        log.info("🕛 Running scheduled task to expire old tokens...");

        // Mark all tokens from previous days as EXPIRED
        int updatedCount = tokenRepo.updateExpiredTokens();

        log.info("✅ Expired {} tokens from previous days.", updatedCount);
    }

    /**
     * Mark stale tokens as MISSED if they've been CALLED for too long or
     * WAITING tokens that are very old.
     * Runs every hour during working hours (8 AM to 8 PM).
     */
    @Scheduled(cron = "0 0 8-20 * * *") // Every hour from 8 AM to 8 PM
    @Transactional
    public void markStaleTokensAsMissed() {
        log.info("🔍 Running scheduled task to mark stale tokens as MISSED...");

        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);

        // Find CALLED tokens that have been called for more than 1 hour
        // These are likely missed by customers
        // Note: Using createdAt as proxy since Token doesn't have updatedAt
        var calledTokens = tokenRepo.findAll().stream()
                .filter(t -> t.getStatus() == TokenStatus.CALLED)
                .filter(t -> t.getCreatedAt() != null && t.getCreatedAt().isBefore(oneHourAgo))
                .toList();

        int missedFromCalled = 0;
        for (Token token : calledTokens) {
            token.setStatus(TokenStatus.MISSED);
            tokenRepo.save(token);
            missedFromCalled++;
            log.debug("Marked token #{} as MISSED (was CALLED for too long)", token.getTokenNumber());
        }

        // Find WAITING tokens that have been waiting for more than 2 hours
        // These are likely abandoned
        var waitingTokens = tokenRepo.findAll().stream()
                .filter(t -> t.getStatus() == TokenStatus.WAITING)
                .filter(t -> t.getCreatedAt().isBefore(twoHoursAgo))
                .toList();

        int missedFromWaiting = 0;
        for (Token token : waitingTokens) {
            token.setStatus(TokenStatus.MISSED);
            tokenRepo.save(token);
            missedFromWaiting++;
            log.debug("Marked token #{} as MISSED (waited too long without being called)", token.getTokenNumber());
        }

        int totalMissed = missedFromCalled + missedFromWaiting;
        if (totalMissed > 0) {
            log.info("✅ Marked {} tokens as MISSED ({} from CALLED, {} from WAITING)",
                    totalMissed, missedFromCalled, missedFromWaiting);
        } else {
            log.info("✅ No stale tokens found.");
        }
    }

    /**
     * Mark all remaining WAITING/CALLED tokens as MISSED at end of business hours.
     * Runs every day at 9 PM (21:00:00).
     */
    @Scheduled(cron = "0 0 21 * * *")
    @Transactional
    public void markUnservedTokensAtClosing() {
        log.info("🏪 Running end-of-day task to mark unserved tokens as MISSED...");

        var unservedTokens = tokenRepo.findAll().stream()
                .filter(t -> t.getStatus() == TokenStatus.WAITING || t.getStatus() == TokenStatus.CALLED)
                .toList();

        int missedCount = 0;
        for (Token token : unservedTokens) {
            token.setStatus(TokenStatus.MISSED);
            tokenRepo.save(token);
            missedCount++;
            log.debug("Marked token #{} as MISSED (office closing)", token.getTokenNumber());
        }

        if (missedCount > 0) {
            log.info("✅ Marked {} unserved tokens as MISSED at closing time.", missedCount);
        } else {
            log.info("✅ All tokens were served today!");
        }
    }
}
