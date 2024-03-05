package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.Survey;

import java.util.Set;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    @Query("SELECT survey FROM Survey survey " +
            "JOIN User user ON survey.id = user.id " +
            "JOIN Token token ON user.id = token.id " +
            "WHERE token.token = :token")
    Set<Survey> findByToken(@Param("token") String token);

}
