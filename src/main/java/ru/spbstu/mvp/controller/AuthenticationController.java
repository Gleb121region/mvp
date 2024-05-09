package ru.spbstu.mvp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.mvp.request.auth.AuthenticationRequest;
import ru.spbstu.mvp.request.auth.ChangePasswordWithEmailRequest;
import ru.spbstu.mvp.request.auth.RegisterRequest;
import ru.spbstu.mvp.response.auth.AuthenticationResponse;
import ru.spbstu.mvp.service.AuthenticationService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PatchMapping("/change-password")
    public AuthenticationResponse changePassword(@RequestBody ChangePasswordWithEmailRequest request) {
        return authenticationService.changePassword(request);
    }

}
