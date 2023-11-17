package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.entity.Photo;

import java.util.Set;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    Set<Photo> findPhotosByFlat(Flat flat);
}
