package ru.spbstu.mvp.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import ru.spbstu.mvp.entity.enums.Gender;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserUpdateRequest {
    @Nullable
    private String firstName;
    @Nullable
    private String LastName;
    @Nullable
    private String AboutMe;
    @Nullable
    private Gender gender;
    @Nullable
    private Integer age;
    @Nullable
    private Date birhdayDate;
    @Nullable
    @Size(max = 11)
    private String mobile;
    @Nullable
    @Email
    private String email;
    @Nullable
    private String linkVK;
}
