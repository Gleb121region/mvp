package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.mvp.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
