package ru.spbstu.mvp.components;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.spbstu.mvp.repository.TokenRepository;

@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {
    private final TokenRepository tokenRepository;
    @Scheduled(fixedRateString = "${milliseconds.in.month}")
    @Modifying
    @Transactional
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredTokens();
    }
}
