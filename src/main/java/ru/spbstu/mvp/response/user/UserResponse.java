package ru.spbstu.mvp.response.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import ru.spbstu.mvp.entity.enums.Gender;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$|^[а-яА-Я]+$")
    private String firstname;

    @Nullable
    @Pattern(regexp = "^[a-zA-Z]+$|^[а-яА-Я]+$")
    private String lastname;

    @Nullable
    private String aboutMe;

    @Nullable
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Nullable
    private Date birthdayDate;

    @Nullable
    @Size(max = 11)
    private String phone;

    @Nullable
    private String linkVK;

    @NotBlank
    @Email
    private String email;
}
