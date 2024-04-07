package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.entity.UserPhoto;
import ru.spbstu.mvp.repository.UserRepository;
import ru.spbstu.mvp.request.user.ChangePasswordRequest;
import ru.spbstu.mvp.request.user.UserUpdateRequest;
import ru.spbstu.mvp.response.user.UserResponse;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public static User getUser(UsernamePasswordAuthenticationToken connectedUser) {
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
        {
            Set<String> photosUrl = user.getPhotos().stream().map(UserPhoto::getPhotoUrl).collect(Collectors.toSet());
            return UserResponse.builder()
                    .userId(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .about(user.getAboutMe())
                    .gender(user.getGender())
                    .birthdayDate(user.getBirthdayDate())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .photosUrl(photosUrl)
                    .build();
        }).orElse(null);
    }

    public void deleteUser(Principal connectedUser) {
        User currentUser = getUser((UsernamePasswordAuthenticationToken) connectedUser);
        repository.deleteUserById(currentUser.getId());
    }
}
