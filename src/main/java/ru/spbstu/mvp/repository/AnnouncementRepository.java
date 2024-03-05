package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.Announcement;
import ru.spbstu.mvp.request.announcement.UpdateAnnouncementRequest;

import java.time.OffsetDateTime;
import java.util.Optional;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    @Query("SELECT DISTINCT announcement FROM Announcement announcement " +
            "LEFT JOIN FETCH announcement.photos " +
            "WHERE announcement.id = :announcementId")
    Optional<Announcement> findByIdWithPhotos(@Param("announcementId") Integer announcementId);

    @Query("SELECT DISTINCT announcement FROM Announcement announcement " +
            "WHERE announcement.createdAt = :createdAt")
    Optional<Announcement> findByCreatedAt(@Param("createdAt") OffsetDateTime createdAt);

    @Modifying
    @Query(value = "UPDATE Announcement announcement SET " +
            "announcement.city = :#{#request.city()}, " +
            "announcement.underground = :#{#request.underground()}, " +
            "announcement.district = :#{#request.district()}, " +
            "announcement.street = :#{#request.street()}, " +
            "announcement.houseNumber = :#{#request.houseNumber()}, " +
            "announcement.floor = :#{#request.floor()}, " +
            "announcement.floorsCount = :#{#request.floorsCount()}, " +
            "announcement.totalMeters = :#{#request.totalMeters()}, " +
            "announcement.apartmentType = :#{#request.apartmentType()}, " +
            "announcement.pricePerMonth = :#{#request.pricePerMonth()}, " +
            "announcement.description = :#{#request.description()}, " +
            "announcement.isRefrigerator = :#{#request.isRefrigerator()}, " +
            "announcement.isWashingMachine = :#{#request.isWashingMachine()}, " +
            "announcement.isTV = :#{#request.isTV()}, " +
            "announcement.isShowerCubicle = :#{#request.isShowerCubicle()}, " +
            "announcement.isBathtub = :#{#request.isBathtub()}, " +
            "announcement.isFurnitureRoom = :#{#request.isFurnitureRoom()}, " +
            "announcement.isFurnitureKitchen = :#{#request.isFurnitureKitchen()}, " +
            "announcement.isDishwasher = :#{#request.isDishwasher()}, " +
            "announcement.isAirConditioning = :#{#request.isAirConditioning()}, " +
            "announcement.isInternet = :#{#request.isInternet()}, " +
            "announcement.isHide = false " +
            "WHERE announcement.id = :#{#request.announcementId()}")
    void updateAnnouncementById(@Param("request") UpdateAnnouncementRequest request);


    @Modifying
    @Query("UPDATE Announcement announcement " +
            "SET announcement.isHide = " +
            "CASE WHEN announcement.createdAt < :monthAgo THEN true " +
            "ELSE announcement.isHide END " +
            "WHERE announcement.createdAt < :monthAgo")
    void updateIsHideFlagToTrueIfBefore(@Param("monthAgo") OffsetDateTime monthAgo);
}

