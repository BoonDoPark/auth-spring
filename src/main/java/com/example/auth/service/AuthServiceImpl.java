package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.SignInRequest;
import com.example.auth.domain.request.SignUpRequest;
import com.example.auth.domain.response.SignInResponse;
import com.example.auth.global.utills.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public SignInResponse signIn(SignInRequest req) {
        Optional<User> byEmail = userRepository.findByEmail(req.email());
        if(byEmail.isEmpty() || !passwordEncoder.matches(req.password(), byEmail.get().getPassword()))
            throw new IllegalArgumentException();
        String token = jwtUtil.generateToken(req.email());

        return SignInResponse.from(token);
    }

    @Override
    @Transactional
    public void insertUser(SignUpRequest req) {
        Optional<User> byEmail = userRepository.findByEmail(req.email());
        if(byEmail.isPresent()) throw new IllegalArgumentException();

        userRepository.save(req.toEntity(passwordEncoder.encode(req.password())));
    }
}
