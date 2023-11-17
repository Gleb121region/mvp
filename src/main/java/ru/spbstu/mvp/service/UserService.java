package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.repository.UserRepository;
import ru.spbstu.mvp.request.auth.ChangePasswordRequest;
import ru.spbstu.mvp.request.user.UserUpdateRequest;
import ru.spbstu.mvp.response.user.UserResponse;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    private static User getUser(UsernamePasswordAuthenticationToken connectedUser) {
        return (User) connectedUser.getPrincipal();
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = getUser((UsernamePasswordAuthenticationToken) connectedUser);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        if (!request.newPassword().equals(request.confirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        repository.save(user);
    }

    public void changeInfoAboutUser(UserUpdateRequest request, Principal connectedUser) {
        User user = getUser((UsernamePasswordAuthenticationToken) connectedUser);
        repository.updateUser(user.getId(), request);
    }

    public UserResponse getInfoAboutUser(Principal connectedUser) {
        User currentUser = getUser((UsernamePasswordAuthenticationToken) connectedUser);
        return repository.findUserById(currentUser.getId()).map(user ->
                UserResponse.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .aboutMe(user.getAboutMe())
                        .gender(user.getGender())
                        .birthdayDate(user.getBirthdayDate())
                        .phone(user.getPhone())
                        .linkVK(user.getLinkVK())
                        .email(user.getEmail())
                        .build()).orElse(null);
    }

    public void deleteUser(Principal connectedUser) {
        User currentUser = getUser((UsernamePasswordAuthenticationToken) connectedUser);
        repository.deleteUserById(currentUser.getId());
    }
}
