package com.example.auth.controller;

import com.example.auth.domain.request.SignInRequest;
import com.example.auth.domain.request.SignUpRequest;
import com.example.auth.domain.response.SignInResponse;
import com.example.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void signup(@RequestBody SignUpRequest req) {
        authService.insertUser(req);
    }

    @PostMapping("/login")
    public SignInResponse signUp(@RequestBody SignInRequest req) {
        return authService.signIn(req);
    }
}
