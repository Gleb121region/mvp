package ru.spbstu.mvp.request.feedback;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.entity.enums.FeedbackType;

public record CreateFeedbackRequest(
        @NotBlank
        FeedbackType feedbackType,
        @NotNull
        User user,
        @NotNull
        Flat flat
) {
}
