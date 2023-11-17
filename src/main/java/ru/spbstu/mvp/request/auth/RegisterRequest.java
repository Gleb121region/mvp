package ru.spbstu.mvp.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import ru.spbstu.mvp.entity.enums.Role;

@Builder
public record RegisterRequest(
        @Pattern(regexp = "^[a-zA-Z]+$|^[а-яА-Я]+$")
        String firstname,
        @Email
        String email,
        @Size(min = 8)
        String password,
        Role role
) {
}
