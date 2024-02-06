package ru.spbstu.mvp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.mvp.request.user.ChangePasswordRequest;
import ru.spbstu.mvp.request.user.UserUpdateRequest;
import ru.spbstu.mvp.response.user.UserResponse;
import ru.spbstu.mvp.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping
    public void changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) {
        userService.changePassword(request, connectedUser);
    }

    @PutMapping
    public void updateUserInfo(@RequestBody UserUpdateRequest request, Principal connectedUser) {
        userService.changeInfoAboutUser(request, connectedUser);
    }

    @GetMapping
    @ResponseBody
    public UserResponse getUser(Principal connectedUser) {
        return userService.getInfoAboutUser(connectedUser);
    }

    @DeleteMapping
    public void deleteUser(Principal connectedUser) {
        userService.deleteUser(connectedUser);
    }
}
