package ru.spbstu.mvp.components;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.spbstu.mvp.repository.TokenRepository;

@Component
public class TokenCleanupScheduler {

    private final TokenRepository tokenRepository;

    public TokenCleanupScheduler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(fixedRateString = "${milliseconds.in.month}")
    @Modifying
    @Transactional
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredTokens();
    }
}
