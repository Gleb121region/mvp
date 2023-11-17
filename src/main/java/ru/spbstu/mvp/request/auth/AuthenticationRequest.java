package ru.spbstu.mvp.request.auth;

import lombok.Builder;

@Builder
public record AuthenticationRequest(
        String email,
        String password
) {
}
