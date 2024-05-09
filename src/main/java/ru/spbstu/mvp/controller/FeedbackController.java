package ru.spbstu.mvp.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.mvp.request.feedback.CreateFeedbackRequest;
import ru.spbstu.mvp.response.announcement.AnnouncementResponse;
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
    public Set<AnnouncementResponse> getLikedFeedbackByUser(Principal connectedUser, @Parameter(description = "Maximum number of announcements to return") @RequestParam(name = "limit") Integer limit, @Parameter(description = "Offset for pagination") @RequestParam(name = "offset") Integer offset) {
        return feedbackService.getLikeFeedbacks(connectedUser, limit, offset);
    }

    @PutMapping("/assess")
    public void createFeedback(@RequestBody CreateFeedbackRequest request, Principal connectedUser) {
        feedbackService.assessFeedback(request, connectedUser);
    }

}
