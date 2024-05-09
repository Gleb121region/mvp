package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.UserPhoto;

import java.util.Set;

@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Integer> {
    Set<UserPhoto> findPhotosByUserId(@NonNull Integer id);
}
