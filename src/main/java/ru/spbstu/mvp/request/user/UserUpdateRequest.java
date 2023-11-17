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
        String firstName,
        @Nullable
        String lastName,
        @Nullable
        String about,
        @Nullable
        Gender gender,
        @Nullable
        Integer age,
        @Nullable
        Date birhdayDate,
        @Nullable
        @Size(max = 11)
        String mobile,
        @Nullable
        @Email
        String email,
        @Nullable
        String linkVK
) {
}
