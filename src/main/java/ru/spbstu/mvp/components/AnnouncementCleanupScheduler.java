package ru.spbstu.mvp.components;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.spbstu.mvp.repository.AnnouncementRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class AnnouncementCleanupScheduler {
    private final AnnouncementRepository announcementRepository;

    @Transactional
    @Scheduled(fixedRateString = "${milliseconds.in.minute}")
    @Async
    public void scheduledHideOfAnnouncement() {
        int ONE_MONTH_AGO = 1;
        LocalDateTime monthAgo = LocalDateTime.now().minusMonths(ONE_MONTH_AGO);
        int MOSCOW_TIME_ZONE = 3;
        OffsetDateTime offsetDateTimeMonthAgo = monthAgo.atOffset(ZoneOffset.ofHours(MOSCOW_TIME_ZONE));
        announcementRepository.updateIsHideFlagToTrueIfBefore(offsetDateTimeMonthAgo);
    }
}
