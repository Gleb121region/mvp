package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spbstu.mvp.repository.FeedbackRepository;
import ru.spbstu.mvp.request.feedback.CreateFeedbackRequest;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private FeedbackRepository feedbackRepository;

    // todo: endpoint который создает объявление
    public void createFeedback(CreateFeedbackRequest request) {
        return;
    }

}
