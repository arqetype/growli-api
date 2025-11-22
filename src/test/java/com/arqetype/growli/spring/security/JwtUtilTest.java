package com.arqetype.growli.spring.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @Value("${security.jwt.secret-key}")
    private String jwtSecret = "testsecretkeytestsecretkeytestsecretkey";

    @Value("${security.jwt.expiration-time}")
    private int jwtExpirationTime = 60000;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        jwtUtil.jwtSecret = jwtSecret;
        jwtUtil.jwtExpirationTime = jwtExpirationTime;
        jwtUtil.init();
    }

    @Test
    void testGenerateToken() {
        String username = "tester";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testGetUsernameFromToken() {
        String username = "tester";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.getUsernameFromToken(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testValidateJwtToken_ValidToken() {
        String username = "tester";
        String token = jwtUtil.generateToken(username);

        assertTrue(jwtUtil.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_InvalidToken() {
        String invalidToken = "invalid.token.value";

        assertFalse(jwtUtil.validateJwtToken(invalidToken));
    }
}
