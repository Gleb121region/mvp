package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.mvp.entity.Dear;
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.entity.User;

import java.util.Set;

public interface DearRepository extends JpaRepository<Dear, Integer> {
    Set<Dear> findDearsByUser(User user);

    Set<Dear> findDearsByUserAndFlat(User user, Flat flat);
}
