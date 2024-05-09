package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.AnnouncementPhoto;

import java.util.Set;

@Repository
public interface AnnouncementPhotoRepository extends JpaRepository<AnnouncementPhoto, Integer> {
    Set<AnnouncementPhoto> findPhotosByAnnouncementId(@NonNull Integer id);
}
