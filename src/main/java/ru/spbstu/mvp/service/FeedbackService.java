package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbstu.mvp.entity.Announcement;
import ru.spbstu.mvp.entity.Feedback;
import ru.spbstu.mvp.entity.Photo;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.entity.enums.FeedbackType;
import ru.spbstu.mvp.exception.AnnouncementNotFoundException;
import ru.spbstu.mvp.exception.UserNotFoundException;
import ru.spbstu.mvp.repository.AnnouncementRepository;
import ru.spbstu.mvp.repository.FeedbackRepository;
import ru.spbstu.mvp.repository.PhotoRepository;
import ru.spbstu.mvp.repository.UserRepository;
import ru.spbstu.mvp.request.feedback.CreateFeedbackRequest;
import ru.spbstu.mvp.response.flat.AnnouncementResponse;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final int SECONDS_PER_MINUTE = 60;
    private final int MILLISECONDS_PER_SECOND = 1000;
    private final int TWO_WEEK_AGO = 2;
    private final int MOSCOW_TIME_ZONE = 3;


    private final FeedbackRepository feedbackRepository;
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    @Transactional
    public void createFeedback(CreateFeedbackRequest request, Principal connectedUser) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
        String userEmail = authenticationToken.getName();

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Announcement> optionalFlat = announcementRepository.findById(request.flatId());
            if (optionalFlat.isPresent()) {
                Feedback feedback = Feedback.builder()
                        .feedbackType(request.feedbackType()).announcement(optionalFlat.get())
                        .user(user)
                        .build();
                feedbackRepository.save(feedback);
            } else {
                throw new AnnouncementNotFoundException("Flat not found");

            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }


    @Transactional(readOnly = true)
    public Set<AnnouncementResponse> getLikeFeedbacks(Principal connectedUser) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
        String userEmail = authenticationToken.getName();

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<Feedback> likedFeedbacks = feedbackRepository.findByFeedbackTypeAndUser(FeedbackType.LIKE, user);
            Set<Announcement> likedAnnouncements = likedFeedbacks.stream().map(Feedback::getAnnouncement).collect(Collectors.toSet());
            return likedAnnouncements.stream().map(announcement -> AnnouncementResponse.builder().id(announcement.getId()).floor(announcement.getFloor()).floorsCount(announcement.getFloorsCount()).totalMeters(announcement.getTotalMeters()).roomsCount(announcement.getRoomsCount()).pricePerMonth(announcement.getPricePerMonth()).address(announcement.getDistrict() + " " + announcement.getStreet() + " " + announcement.getHouseNumber()).underground(announcement.getUnderground()).photoUrls(photoRepository.findPhotosByAnnouncementId(announcement.getId()).stream().map(Photo::getPhotoUrl).collect(Collectors.toSet()))
                            .build()
            ).collect(Collectors.toSet());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }


    @Transactional
    @Scheduled(fixedRate = SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND)
    @Async
    public void scheduledRestorationOfAnnouncements() {
        LocalDateTime monthAgo = LocalDateTime.now().minusWeeks(TWO_WEEK_AGO);
        OffsetDateTime offsetDateTimeTwoWeekAgo = monthAgo.atOffset(ZoneOffset.ofHours(MOSCOW_TIME_ZONE));
        feedbackRepository.deleteOldFeedback(offsetDateTimeTwoWeekAgo);
    }
}
