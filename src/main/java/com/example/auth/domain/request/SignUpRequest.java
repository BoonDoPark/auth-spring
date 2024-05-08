package com.example.auth.domain.request;

import com.example.auth.domain.entity.User;

import java.time.LocalDate;

public record SignUpRequest (
        String email,
        String password,
        String nickName,
        LocalDate birthDay,
        String gender
) {
    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(nickName)
                .birthDay(birthDay)
                .gender(gender)
                .build();
    }
}
