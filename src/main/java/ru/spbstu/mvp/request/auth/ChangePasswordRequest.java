package ru.spbstu.mvp.request.auth;

import lombok.Builder;

@Builder
public record ChangePasswordRequest(
        String currentPassword,
        String newPassword,
        String confirmationPassword
) {
}
