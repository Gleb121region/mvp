package ru.spbstu.mvp.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.lang.Nullable;
import ru.spbstu.mvp.entity.enums.Gender;

import java.util.Date;


@Builder
public record UserUpdateRequest(
        @Nullable
        String firstname,
        @Nullable
        String lastname,
        @Nullable
        String about,
        @Nullable
        Gender gender,
        @Nullable
        Date birthdayDate,
        @Nullable
        @Size(max = 11)
        String phone,
        @Nullable
        @Email
        String email) {
}
