package ru.spbstu.mvp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbstu.mvp.entity.Announcement;
import ru.spbstu.mvp.entity.AnnouncementPhoto;
import ru.spbstu.mvp.entity.Feedback;
import ru.spbstu.mvp.entity.enums.FeedbackType;
import ru.spbstu.mvp.mapper.AnnouncementMapper;
import ru.spbstu.mvp.repository.AnnouncementRepository;
import ru.spbstu.mvp.request.announcement.AnnouncementRequest;
import ru.spbstu.mvp.request.announcement.CreateAnnouncementRequest;
import ru.spbstu.mvp.request.announcement.UpdateAnnouncementRequest;
import ru.spbstu.mvp.response.flat.AnnouncementResponse;
import ru.spbstu.mvp.response.flat.AnnouncementWithDescriptionResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final int SECONDS_PER_MINUTE = 60;
    private final int MILLISECONDS_PER_SECOND = 1000;
    private final int ONE_MONTH_AGO = 1;
    private final int MOSCOW_TIME_ZONE = 3;

    private final AnnouncementRepository announcementRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Page<Announcement> findAnnouncementsByParams(AnnouncementRequest request, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcement> criteriaQuery = criteriaBuilder.createQuery(Announcement.class);
        Root<Announcement> announcement = criteriaQuery.from(Announcement.class);
        announcement.fetch("photos", JoinType.LEFT);
        Join<Announcement, Feedback> feedbackJoin = announcement.join("feedbacks", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(announcement.get("isOld"), false));
        predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(feedbackJoin.get("id")), criteriaBuilder.notEqual(feedbackJoin.get("feedbackType"), FeedbackType.DISLIKE)));

        if (request.city() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("city"), request.city()));
        }
        if (request.underground() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("underground"), request.underground()));
        }
        if (request.district() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("district"), request.district()));
        }
        if (request.roomsCounts() != null) {
            predicates.add(announcement.get("roomsCount").in(request.roomsCounts()));
        }
        if (request.maxPricePerMonth() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(announcement.get("pricePerMonth"), request.maxPricePerMonth()));
        }
        if (request.minPricePerMonth() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(announcement.get("pricePerMonth"), request.minPricePerMonth()));
        }
        if (request.isRefrigerator() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("isRefrigerator"), request.isRefrigerator()));
        }
        if (request.isWashingMachine() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("isWashingMachine"), request.isWashingMachine()));
        }
        if (request.isTV() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("isTV"), request.isTV()));
        }
        if (request.isShowerCubicle() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("isShowerCubicle"), request.isShowerCubicle()));
        }
        if (request.isBathtub() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("isBathtub"), request.isBathtub()));
        }
        if (request.isFurnitureRoom() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("isFurnitureRoom"), request.isFurnitureRoom()));
        }
        if (request.isFurnitureKitchen() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("isFurnitureKitchen"), request.isFurnitureKitchen()));
        }
        if (request.isDishwasher() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("isDishwasher"), request.isDishwasher()));
        }
        if (request.isAirConditioning() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("isAirConditioning"), request.isAirConditioning()));
        }
        if (request.isInternet() != null) {
            predicates.add(criteriaBuilder.equal(announcement.get("isInternet"), request.isInternet()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Announcement> query = entityManager.createQuery(criteriaQuery);
        return new PageImpl<>(query.getResultList(), pageable, query.getResultList().size());
    }

    @Transactional(readOnly = true)
    public Set<AnnouncementResponse> getAnnouncementsInfo(@Valid AnnouncementRequest request, Integer limit, Integer offset) {
        Page<Announcement> announcements = findAnnouncementsByParams(request, PageRequest.of(offset, limit));
        return announcements.map(announcement -> AnnouncementResponse.builder().id(announcement.getId()).floor(announcement.getFloor()).floorsCount(announcement.getFloorsCount()).totalMeters(announcement.getTotalMeters()).roomsCount(announcement.getRoomsCount()).pricePerMonth(announcement.getPricePerMonth()).address(announcement.getDistrict() + " " + announcement.getStreet() + " " + announcement.getHouseNumber()).underground(announcement.getUnderground()).photoUrls(announcement.getPhotos().stream().map(AnnouncementPhoto::getPhotoUrl).collect(Collectors.toSet()))
                        .build()
        ).stream().collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public AnnouncementWithDescriptionResponse getAnnouncementInfo(int announcementId) {
        return announcementRepository.findByIdWithPhotos(announcementId).map(announcement -> AnnouncementWithDescriptionResponse.builder().id(announcement.getId()).floor(announcement.getFloor()).floorsCount(announcement.getFloorsCount()).totalMeters(announcement.getTotalMeters()).roomsCount(announcement.getRoomsCount()).pricePerMonth(announcement.getPricePerMonth()).address(announcement.getDistrict() + " " + announcement.getStreet() + " " + announcement.getHouseNumber()).underground(announcement.getUnderground()).photoUrls(announcement.getPhotos().stream().map(AnnouncementPhoto::getPhotoUrl).collect(Collectors.toSet())).description(announcement.getDescription())
                                .build()
        ).orElse(null);
    }

    @Transactional
    public Integer createAnnouncementFromRequestWithoutPhoto(CreateAnnouncementRequest request) {
        Announcement announcement = AnnouncementMapper.mapCreateAnnouncementRequestToAnnouncement(request);
        announcementRepository.save(announcement);
        OffsetDateTime createdAt = announcement.getCreatedAt();
        return announcementRepository
                .findByCreatedAt(createdAt)
                .map(Announcement::getId)
                .orElse(null);
    }


    @Transactional
    public void updateAnnouncementByAnnouncementId(UpdateAnnouncementRequest request) {
        announcementRepository.updateAnnouncementById(request);
    }

    @Transactional
    @Scheduled(fixedRate = SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND)
    @Async
    public void scheduledHideOfAnnouncement() {
        LocalDateTime monthAgo = LocalDateTime.now().minusMonths(ONE_MONTH_AGO);
        OffsetDateTime offsetDateTimeMonthAgo = monthAgo.atOffset(ZoneOffset.ofHours(MOSCOW_TIME_ZONE));
        announcementRepository.updateIsHideFlagToTrueIfBefore(offsetDateTimeMonthAgo);
    }

}
