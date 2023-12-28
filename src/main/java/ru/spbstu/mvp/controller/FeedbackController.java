package ru.spbstu.mvp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.mvp.request.feedback.CreateFeedbackRequest;
import ru.spbstu.mvp.response.flat.FlatLikedResponse;
import ru.spbstu.mvp.service.FeedbackService;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public FlatLikedResponse getLikedFeedbackByUser() {
        return null;
    }

    @PostMapping
    public void createFeedback(CreateFeedbackRequest request) {
        feedbackService.createFeedback(request);
    }

}
