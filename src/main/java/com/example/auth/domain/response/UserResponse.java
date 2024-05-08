package com.example.auth.domain.response;

import com.example.auth.domain.entity.User;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String nickName,
        LocalDate birthDay,
        String gender
) {
    public UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getBirthDay(),
                user.getGender()
        );
    }
}
