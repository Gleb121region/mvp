package ru.spbstu.mvp.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.Announcement;
import ru.spbstu.mvp.entity.Feedback;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.entity.enums.FeedbackType;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("SELECT f FROM Feedback f WHERE f.feedbackType = :feedbackType AND f.user = :user")
    Page<Feedback> findByFeedbackTypeAndUser(@Param("feedbackType") FeedbackType feedbackType, @Param("user") User user, Pageable pageable);


    Optional<Feedback> findByAnnouncementAndUser(@NonNull Announcement announcement, @NotNull User user);

    @Modifying
    @Query("DELETE FROM Feedback f WHERE f.createdAt < :cutoffDate")
    void deleteOldFeedback(OffsetDateTime cutoffDate);

}
