package ru.spbstu.mvp.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spbstu.mvp.entity.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Pattern(regexp = "^[a-zA-Z]+$|^[а-яА-Я]+$")
    private String firstname;
    @Email
    private String email;
    @Size(min = 8)
    private String password;
    private Role role;
}
