package com.sevaqueue.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
     * Runs every day at midnight.
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void expireOldTokens() {
        log.info("DEBUG: Running scheduled task to expire old tokens...");

        // Find tokens where createdAt < TODAY and status in (WAITING, CALLED)
        // We'll use a direct JPQL update for efficiency
        int updatedCount = tokenRepo.updateExpiredTokens();

        log.info("DEBUG: Expired {} tokens from previous days.", updatedCount);
    }
}
