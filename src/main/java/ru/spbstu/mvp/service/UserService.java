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

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public void changeInfoAboutUser(UserUpdateRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        repository.updateUser(user.getId(), request);
    }

    public UserResponse getInfoAboutUser(Principal connectedUser) {
        User currentUser = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
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
}
