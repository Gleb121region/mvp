package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.Flat;

import java.util.Optional;

//todo: нужно решить проблему с N+1, можно использовать https://www.baeldung.com/jpa-entity-graph
// N+1 решает проблему, но есть самая главная проблема с получением данных из таблицы Photo поля photo_url

@Repository
public interface FlatRepository extends JpaRepository<Flat, Integer> {

    @Override
    Optional<Flat> findById(Integer integer);
}

