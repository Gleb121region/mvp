package ru.spbstu.mvp.response.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.lang.Nullable;
import ru.spbstu.mvp.entity.enums.Gender;

import java.util.Date;
import java.util.Set;

@Builder
public record UserResponse(
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]+$|^[а-яА-Я]+$")
        String firstname,
        @Nullable
        @Pattern(regexp = "^[a-zA-Z]+$|^[а-яА-Я]+$")
        String lastname,
        @Nullable
        String about,
        @Nullable
        @Enumerated(EnumType.STRING)
        Gender gender,
        @Nullable
        Date birthdayDate,
        @Nullable
        @Size(max = 11)
        String phone,
        @NotBlank
        @Email
        String email,
        @Nullable
        Set<String> photosUrl
) {
}
