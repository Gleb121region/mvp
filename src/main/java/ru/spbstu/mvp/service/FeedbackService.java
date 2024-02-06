package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbstu.mvp.entity.Feedback;
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.entity.Photo;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.entity.enums.FeedbackType;
import ru.spbstu.mvp.repository.FeedbackRepository;
import ru.spbstu.mvp.repository.FlatRepository;
import ru.spbstu.mvp.repository.PhotoRepository;
import ru.spbstu.mvp.request.feedback.CreateFeedbackRequest;
import ru.spbstu.mvp.response.flat.FlatResponse;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.spbstu.mvp.service.UserService.getUser;


@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FlatRepository flatRepository;
    private final PhotoRepository photoRepository;

    @Transactional
    public void createFeedback(CreateFeedbackRequest request, Principal connectedUser) {
        User user = getUser((UsernamePasswordAuthenticationToken) connectedUser);
        Optional<Flat> optionalFlat = flatRepository.findById(request.flatId());
        if (optionalFlat.isPresent()) {
            Feedback feedback = Feedback.builder()
                    .feedbackType(request.feedbackType())
                    .flat(optionalFlat.get())
                    .user(user)
                    .build();
            feedbackRepository.save(feedback);
        }
    }

    @Transactional(readOnly = true)
    public Set<FlatResponse> getLikeFeedbacks(Principal connectedUser) {
        User user = getUser((UsernamePasswordAuthenticationToken) connectedUser);
        Set<Feedback> likedFeedbacks = feedbackRepository.findByFeedbackTypeAndUser(FeedbackType.LIKE, user);
        Set<Flat> likedFlats = likedFeedbacks.stream().map(Feedback::getFlat).collect(Collectors.toSet());
        return likedFlats.stream().map(
                flat -> FlatResponse.builder()
                        .id(flat.getId())
                        .floor(flat.getFloor())
                        .floorsCount(flat.getFloorsCount())
                        .totalMeters(flat.getTotalMeters())
                        .roomsCount(flat.getRoomsCount())
                        .pricePerMonth(flat.getPricePerMonth())
                        .address(flat.getDistrict() + " " + flat.getStreet() + " " + flat.getHouseNumber())
                        .underground(flat.getUnderground())
                        .photoUrls(photoRepository.findPhotosByFlatId(flat.getId()).stream().map(Photo::getPhotoUrl).collect(Collectors.toSet()))
                        .build()
        ).collect(Collectors.toSet());
    }
}
