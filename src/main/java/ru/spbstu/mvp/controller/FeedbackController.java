package ru.spbstu.mvp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.mvp.request.feedback.CreateFeedbackRequest;
import ru.spbstu.mvp.response.flat.FlatResponse;
import ru.spbstu.mvp.service.FeedbackService;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/liked")
    @ResponseBody
    public Set<FlatResponse> getLikedFeedbackByUser(Principal connectedUser) {
        return feedbackService.getLikeFeedbacks(connectedUser);
    }

    @PostMapping("/create")
    public void createFeedback(CreateFeedbackRequest request, Principal connectedUser) {
        feedbackService.createFeedback(request, connectedUser);
    }

}
