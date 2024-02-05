package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.Feedback;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.entity.enums.FeedbackType;

import java.util.Set;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    Set<Feedback> findByFeedbackTypeAndUser(@NonNull FeedbackType feedbackType, @NonNull User user);

}
