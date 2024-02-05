package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.entity.Photo;

import java.util.Set;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    Set<Photo> findPhotosByFlatId(@NonNull Integer id);
}
