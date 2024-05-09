package ru.spbstu.mvp.request.auth;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ChangePasswordWithEmailRequest(
        String email,
        @Size(min = 8)
        String newPassword,
        @Size(min = 8)
        String confirmationPassword
) {
}
