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
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateUserInfo(@RequestBody UserUpdateRequest request, Principal connectedUser) {
        userService.changeInfoAboutUser(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getUser(Principal connectedUser) {
        UserResponse userResponse = userService.getInfoAboutUser(connectedUser);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(Principal connectedUser) {
        userService.deleteUser(connectedUser);
        return ResponseEntity.ok().build();
    }
}
