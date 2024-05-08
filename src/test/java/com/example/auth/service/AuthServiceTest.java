package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.SignInRequest;
import com.example.auth.domain.request.SignUpRequest;
import com.example.auth.domain.response.SignInResponse;
import com.example.auth.global.exception.ExistedUserException;
import com.example.auth.global.utills.JwtUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Nested
    class 로그인 {
        @Test
        void 성공() {
            // given
            User user = User.builder().email("t@t.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("tt")
                    .gender("남")
                    .birthDay(LocalDate.of(1990, 1, 1))
                    .build();
            userRepository.save(user);
            SignInRequest req = new SignInRequest("t@t.com", "1234");

            // when
            SignInResponse res = authService.signIn(req);

            // then
            assertNotNull(res.token());
            assertEquals(3, res.token().split("\\.").length);
            assertEquals("Bearer", res.tokenType());
        }
        @Test
        void 실패_이메일() {
            // given
            User user = User.builder().email("t@t.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("tt")
                    .gender("남")
                    .birthDay(LocalDate.of(1990, 1, 1))
                    .build();
            userRepository.save(user);


            // when
            SignInRequest req = new SignInRequest("a@a.com", "1234");

            //then
            assertThrows(ExistedUserException.class, () -> authService.signIn(req));
        }

        @Test
        void 실패_비밀번호() {
            // given
            User user = User.builder().email("t@t.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("tt")
                    .gender("남")
                    .birthDay(LocalDate.of(1990, 1, 1))
                    .build();
            userRepository.save(user);


            // when
            SignInRequest req = new SignInRequest("t@t.com", "123");

            //then
            assertThrows(ExistedUserException.class, () -> authService.signIn(req));
        }
    }

    @Nested
    class 회원가입 {
        @Test
        void 성공(){
            // given
            SignUpRequest signUpRequest = new SignUpRequest(
                    "a@b.com",
                    "1234",
                    "sss",
                    LocalDate.of(2000, 4, 7),
                    "남"
            );

            // when
            authService.insertUser(signUpRequest);

            // then
            Optional<User> byEmail = userRepository.findByEmail(signUpRequest.email());
            assertTrue(byEmail.isPresent());
            assertNotEquals("1234", byEmail.get().getPassword());


        }

        @Test
        void 실패_이미_있는_이메일(){
            // given
            SignUpRequest signUpRequest = new SignUpRequest(
                    "a@b.com",
                    "1234",
                    "sss",
                    LocalDate.of(2000, 4, 7),
                    "남"
            );
            userRepository.save(User.builder().email("a@b.com").build());

            // when & then
            assertThrows(ExistedUserException.class,
                    () -> authService.insertUser(signUpRequest)
            );
        }
    }

    @Test
    void insertUser() {

    }

}