package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.SurveyAnswers;
import ru.spbstu.mvp.entity.User;

import java.util.Set;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyAnswers, Integer> {
    Set<SurveyAnswers> findByUser(User user);
}
