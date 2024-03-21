package ru.spbstu.mvp.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.Announcement;
import ru.spbstu.mvp.entity.Feedback;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.entity.enums.FeedbackType;

import java.time.OffsetDateTime;
import java.util.Set;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    Set<Feedback> findByFeedbackTypeAndUser(@NonNull FeedbackType feedbackType, @NonNull User user);

    Feedback findByAnnouncementAndUser(@NonNull Announcement announcement, @NotNull User user);

    @Modifying
    @Query("DELETE FROM Feedback f WHERE f.createdAt < :cutoffDate")
    void deleteOldFeedback(OffsetDateTime cutoffDate);

}
