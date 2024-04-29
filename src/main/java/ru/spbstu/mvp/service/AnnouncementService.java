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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbstu.mvp.entity.Announcement;
import ru.spbstu.mvp.entity.AnnouncementPhoto;
import ru.spbstu.mvp.entity.Feedback;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.entity.enums.FeedbackType;
import ru.spbstu.mvp.exception.UserNotFoundException;
import ru.spbstu.mvp.mapper.AnnouncementMapper;
import ru.spbstu.mvp.repository.AnnouncementRepository;
import ru.spbstu.mvp.repository.FeedbackRepository;
import ru.spbstu.mvp.repository.UserRepository;
import ru.spbstu.mvp.request.announcement.AnnouncementRequest;
import ru.spbstu.mvp.request.announcement.CreateAnnouncementRequest;
import ru.spbstu.mvp.request.announcement.UpdateAnnouncementRequest;
import ru.spbstu.mvp.response.announcement.AnnouncementResponse;
import ru.spbstu.mvp.response.announcement.AnnouncementWithDescriptionResponse;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Boolean checkIfLikedByUser(Announcement announcement, Principal connectedUser) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
        String userEmail = authenticationToken.getName();
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        boolean isLiked = false;
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            Optional<Feedback> optionalFeedback = feedbackRepository.findByAnnouncementAndUser(announcement, currentUser);
            if (optionalFeedback.isPresent()) {
                FeedbackType feedbackType = optionalFeedback.get().getFeedbackType();
                if (feedbackType.equals(FeedbackType.LIKE)) {
                    isLiked = true;
                }
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
        return isLiked;
    }

    private Page<Announcement> findAnnouncementsByParams(AnnouncementRequest request, Pageable pageable, Principal connectedUser) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
        String userEmail = authenticationToken.getName();

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Announcement> criteriaQuery = criteriaBuilder.createQuery(Announcement.class);
            Root<Announcement> announcement = criteriaQuery.from(Announcement.class);
            announcement.fetch("photos", JoinType.LEFT);
            Join<Announcement, Feedback> feedbackJoin = announcement.join("feedbacks", JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();

            Predicate isHide = criteriaBuilder.equal(announcement.get("isHide"), false);
            predicates.add(isHide);

            Predicate feedbackIsNull = criteriaBuilder.isNull(feedbackJoin.get("id"));

            Integer userId = optionalUser.get().getId();
            FeedbackType dislike = FeedbackType.DISLIKE;

            Predicate feedbackIsNotMine = criteriaBuilder.notEqual(feedbackJoin.get("user").get("id"), userId);
            Predicate feedbackTypeIsDislike = criteriaBuilder.equal(feedbackJoin.get("feedbackType"), dislike);

            // Другие люди с мнением Dislike
            Predicate feedbackIsNormalForOther = criteriaBuilder.and(feedbackIsNotMine, feedbackTypeIsDislike);

            Predicate feedbackIsMine = criteriaBuilder.equal(feedbackJoin.get("user").get("id"), userId);
            Predicate feedbackTypeIsNotDislike = criteriaBuilder.notEqual(feedbackJoin.get("feedbackType"), dislike);

            // Я с мнением отличным от Dislike
            Predicate feedbackIsNormalForMe = criteriaBuilder.and(feedbackIsMine, feedbackTypeIsNotDislike);

            Predicate feedbackIsNormal = criteriaBuilder.or(feedbackIsNormalForOther, feedbackIsNormalForMe);

            predicates.add(criteriaBuilder.or(feedbackIsNull, feedbackIsNormal));

            if (request.city() != null) {
                predicates.add(criteriaBuilder.equal(announcement.get("city"), request.city()));
            }
            if (request.underground() != null) {
                predicates.add(criteriaBuilder.equal(announcement.get("underground"), request.underground()));
            }
            if (request.district() != null) {
                predicates.add(criteriaBuilder.equal(announcement.get("district"), request.district()));
            }
            if (request.apartmentTypes() != null) {
                predicates.add(announcement.get("apartmentType").in(request.apartmentTypes()));
            }
            if (request.maxPricePerMonth() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(announcement.get("pricePerMonth"), request.maxPricePerMonth()));
            }
            if (request.minPricePerMonth() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(announcement.get("pricePerMonth"), request.minPricePerMonth()));
            }
            if (request.maxPricePerMonth() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(announcement.get("totalMeters"), request.maxArea()));
            }
            if (request.minPricePerMonth() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(announcement.get("totalMeters"), request.minArea()));
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

            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());

            return new PageImpl<>(query.getResultList(), pageable, query.getResultList().size());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional(readOnly = true)
    public Set<AnnouncementResponse> getAnnouncementsInfo(@Valid AnnouncementRequest request, Integer limit, Integer offset, Principal connectedUser) {
        int offsetValue = offset != null ? offset : 0;

        Page<Announcement> announcements = findAnnouncementsByParams(request, PageRequest.of(offsetValue, limit), connectedUser);
        return announcements.map(it ->
        {
            String address = it.getDistrict() + " " + it.getStreet() + " " + it.getHouseNumber();
            Stream<String> stringStream = it.getPhotos().stream().map(AnnouncementPhoto::getPhotoUrl);
            return AnnouncementResponse.builder()
                    .id(it.getId())
                    .floor(it.getFloor())
                    .floorsCount(it.getFloorsCount())
                    .totalMeters(it.getTotalMeters())
                    .apartmentType(it.getApartmentType())
                    .pricePerMonth(it.getPricePerMonth())
                    .address(address)
                    .underground(it.getUnderground())
                    .photoUrls(stringStream.collect(Collectors.toSet()))
                    .isLikedByUser(checkIfLikedByUser(it, connectedUser))
                    .build();
        }).stream().collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public AnnouncementWithDescriptionResponse getAnnouncementInfo(int announcementId, Principal connectedUser) {
        return announcementRepository.findByIdWithPhotos(announcementId).map(it ->
        {
            String address = it.getDistrict() + " " + it.getStreet() + " " + it.getHouseNumber();
            Stream<String> stringStream = it.getPhotos().stream().map(AnnouncementPhoto::getPhotoUrl);
            return AnnouncementWithDescriptionResponse.builder()
                    .id(it.getId())
                    .city(it.getCity())
                    .floor(it.getFloor())
                    .floorsCount(it.getFloorsCount())
                    .totalMeters(it.getTotalMeters())
                    .apartmentType(it.getApartmentType())
                    .pricePerMonth(it.getPricePerMonth())
                    .address(address)
                    .underground(it.getUnderground())
                    .photoUrls(stringStream.collect(Collectors.toSet()))
                    .description(it.getDescription())
                    .isRefrigerator(it.getIsRefrigerator())
                    .isWashingMachine(it.getIsWashingMachine())
                    .isTV(it.getIsTV())
                    .isShowerCubicle(it.getIsShowerCubicle())
                    .isBathtub(it.getIsBathtub())
                    .isFurnitureRoom(it.getIsFurnitureRoom())
                    .isFurnitureKitchen(it.getIsFurnitureKitchen())
                    .isDishwasher(it.getIsDishwasher())
                    .isAirConditioning(it.getIsAirConditioning())
                    .isInternet(it.getIsInternet())
                    .isHide(it.getIsHide())
                    .isLikedByUser(checkIfLikedByUser(it, connectedUser))
                    .build();
        }).orElse(null);
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

}
