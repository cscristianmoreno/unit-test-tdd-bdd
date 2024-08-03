package com.app.test.security;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomPasswordEncoderTest {

    @Test
    void passwordEncoderTest() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = "user";
        String compare = bCryptPasswordEncoder.encode("user");

        System.out.println(compare);

        assertTrue(bCryptPasswordEncoder.matches(password, compare));
    }
}
