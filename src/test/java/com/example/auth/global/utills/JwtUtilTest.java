package com.example.auth.global.utills;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    private JwtUtil jwtUtil = new JwtUtil("asfkjji2i42i9sfisjdfnljskdfakldsksd", 1000*2L);
    @Test
    void generateToken() {
        String email = "a@a.com";
        String token = jwtUtil.generateToken(email);

        assertNotNull(token);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void getByEmailFromTokenAndValidation() {
        String email = "a@a.com";
        String token = jwtUtil.generateToken(email);

        String answer = jwtUtil.getByEmailFromTokenAndValidation(token);

        assertNotNull(answer);
        assertEquals(email, answer);
    }

    @Test
    void 시간_만료() throws InterruptedException {
        String email = "a@a.com";
        String token = jwtUtil.generateToken(email);
        Thread.sleep(2000L);

        assertThrows(JwtException.class, () -> jwtUtil.getByEmailFromTokenAndValidation(token));
    }
}