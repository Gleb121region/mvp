package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbstu.mvp.entity.Announcement;
import ru.spbstu.mvp.entity.AnnouncementPhoto;
import ru.spbstu.mvp.entity.Feedback;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.entity.enums.FeedbackType;
import ru.spbstu.mvp.exception.AnnouncementNotFoundException;
import ru.spbstu.mvp.exception.UserNotFoundException;
import ru.spbstu.mvp.repository.AnnouncementPhotoRepository;
import ru.spbstu.mvp.repository.AnnouncementRepository;
import ru.spbstu.mvp.repository.FeedbackRepository;
import ru.spbstu.mvp.repository.UserRepository;
import ru.spbstu.mvp.request.feedback.CreateFeedbackRequest;
import ru.spbstu.mvp.response.announcement.AnnouncementResponse;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final AnnouncementPhotoRepository announcementPhotoRepository;

    @Modifying
    public void assessFeedback(CreateFeedbackRequest request, Principal connectedUser) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
        String userEmail = authenticationToken.getName();

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Optional<Announcement> optionalAnnouncement = announcementRepository.findById(request.announcementId());
            if (optionalAnnouncement.isPresent()) {
                Announcement announcement = optionalAnnouncement.get();

                Optional<Feedback> optionalFeedback = feedbackRepository.findByAnnouncementAndUser(announcement, user);
                Feedback feedback;
                FeedbackType type = request.feedbackType();
                if (optionalFeedback.isPresent()) {
                    feedback = optionalFeedback.get();
                    feedback.setFeedbackType(type);
                } else {
                    feedback = Feedback.builder().feedbackType(type).announcement(announcement).user(user).build();
                }
                feedbackRepository.save(feedback);
            } else {
                throw new AnnouncementNotFoundException("Announcement not found");
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
            return likedAnnouncements.stream().map(announcement -> AnnouncementResponse.builder().id(announcement.getId()).floor(announcement.getFloor()).floorsCount(announcement.getFloorsCount()).totalMeters(announcement.getTotalMeters()).apartmentType(announcement.getApartmentType()).pricePerMonth(announcement.getPricePerMonth()).address(announcement.getDistrict() + " " + announcement.getStreet() + " " + announcement.getHouseNumber()).underground(announcement.getUnderground()).photoUrls(announcementPhotoRepository.findPhotosByAnnouncementId(announcement.getId()).stream().map(AnnouncementPhoto::getPhotoUrl).collect(Collectors.toSet()))
                            .build()
            ).collect(Collectors.toSet());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

}
