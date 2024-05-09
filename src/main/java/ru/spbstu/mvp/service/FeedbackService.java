package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import ru.spbstu.mvp.exception.AnnouncementNotFoundException;
import ru.spbstu.mvp.exception.UserNotFoundException;
import ru.spbstu.mvp.repository.AnnouncementPhotoRepository;
import ru.spbstu.mvp.repository.AnnouncementRepository;
import ru.spbstu.mvp.repository.FeedbackRepository;
import ru.spbstu.mvp.repository.UserRepository;
import ru.spbstu.mvp.request.feedback.CreateFeedbackRequest;
import ru.spbstu.mvp.response.announcement.AnnouncementResponse;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final AnnouncementPhotoRepository announcementPhotoRepository;

    @Transactional
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
                FeedbackType type = request.feedbackType();
                if (optionalFeedback.isPresent()) {
                    if (type.equals(FeedbackType.DEFAULT)) {
                        feedbackRepository.deleteByAnnouncement(announcement);
                    } else {
                        Feedback feedback = optionalFeedback.get();
                        feedback.setFeedbackType(type);
                        feedbackRepository.save(feedback);
                    }
                } else {
                    if (!type.equals(FeedbackType.DEFAULT)) {
                        Feedback feedback = Feedback.builder()
                                .feedbackType(type)
                                .announcement(announcement)
                                .user(user)
                                .build();
                        feedbackRepository.save(feedback);
                    }
                }
            } else {
                throw new AnnouncementNotFoundException("Announcement not found");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional(readOnly = true)
    public Set<AnnouncementResponse> getLikeFeedbacks(Principal connectedUser, int limit, int offset) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
        String userEmail = authenticationToken.getName();

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Pageable pageable = PageRequest.of(offset, limit);
            Page<Feedback> likedFeedbacksPage = feedbackRepository.findByFeedbackTypeAndUser(FeedbackType.LIKE, user, pageable);
            List<Feedback> likedFeedbacks = likedFeedbacksPage.getContent();
            Set<Announcement> likedAnnouncements = likedFeedbacks.stream().map(Feedback::getAnnouncement).collect(Collectors.toSet());
            return likedAnnouncements.stream().map(it ->
                    {
                        Integer id = it.getId();
                        String address = it.getDistrict() + " " + it.getStreet() + " " + it.getHouseNumber();
                        Set<AnnouncementPhoto> photos = announcementPhotoRepository.findPhotosByAnnouncementId(id);
                        Stream<String> stream = photos.stream().map(AnnouncementPhoto::getPhotoUrl);
                        return AnnouncementResponse.builder()
                                .id(id)
                                .floor(it.getFloor())
                                .floorsCount(it.getFloorsCount())
                                .totalMeters(it.getTotalMeters())
                                .apartmentType(it.getApartmentType())
                                .pricePerMonth(it.getPricePerMonth())
                                .address(address)
                                .underground(it.getUnderground())
                                .photoUrls(stream.collect(Collectors.toSet()))
                                .isLikedByUser(true)
                                .build();
                    })
                    .collect(Collectors.toSet());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}
