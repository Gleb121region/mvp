package ru.spbstu.mvp.components;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.spbstu.mvp.repository.FeedbackRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class FeedbackCleanupScheduler {
    private final FeedbackRepository feedbackRepository;

    @Transactional
    @Scheduled(fixedRateString = "${milliseconds.in.minute}")
    @Async
    public void scheduledRestorationOfAnnouncements() {
        int TWO_WEEK_AGO = 2;
        LocalDateTime monthAgo = LocalDateTime.now().minusWeeks(TWO_WEEK_AGO);
        int MOSCOW_TIME_ZONE = 3;
        OffsetDateTime offsetDateTimeTwoWeekAgo = monthAgo.atOffset(ZoneOffset.ofHours(MOSCOW_TIME_ZONE));
        feedbackRepository.deleteOldFeedback(offsetDateTimeTwoWeekAgo);
    }
}
