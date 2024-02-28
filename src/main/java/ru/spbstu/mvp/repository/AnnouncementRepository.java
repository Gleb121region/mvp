package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.Announcement;

import java.time.OffsetDateTime;
import java.util.Optional;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    @Query("SELECT DISTINCT flat FROM Announcement flat LEFT JOIN FETCH flat.photos WHERE flat.id = :flatId")
    Optional<Announcement> findByIdWithPhotos(@Param("flatId") Integer flatId);


    @Modifying
    @Query("UPDATE Announcement a SET a.isHide = CASE WHEN a.createdAt < :monthAgo THEN true ELSE a.isHide END WHERE a.createdAt < :monthAgo")
    void updateIsHideFlagToTrueIfBefore(@Param("monthAgo") OffsetDateTime monthAgo);
}

