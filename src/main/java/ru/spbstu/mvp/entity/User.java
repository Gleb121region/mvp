package ru.spbstu.mvp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.spbstu.mvp.entity.enums.Gender;
import ru.spbstu.mvp.entity.enums.Role;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

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

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^[~`!@#$%^&*()_+\\-=\\[\\]\\\\{}|;':\",./<>?a-zA-Z0-9]+$")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
