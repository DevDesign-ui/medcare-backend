package com.medcare.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        String secret = "medcare-test-secret-key-which-is-very-long-and-secure-for-testing-purposes-only-1234567890";
        String encoded = java.util.Base64.getEncoder().encodeToString(secret.getBytes());
        jwtProvider = new JwtProvider(encoded, 86400000);
    }

    @Test
    void generateToken_shouldCreateValidToken() {
        String token = jwtProvider.generateToken(1L, "admin@medcare.com", "ADMIN");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void validateToken_shouldReturnTrueForValidToken() {
        String token = jwtProvider.generateToken(1L, "admin@medcare.com", "ADMIN");
        assertTrue(jwtProvider.validateToken(token));
    }

    @Test
    void validateToken_shouldReturnFalseForInvalidToken() {
        assertFalse(jwtProvider.validateToken("invalid.token.here"));
    }

    @Test
    void getEmailFromToken_shouldReturnCorrectEmail() {
        String token = jwtProvider.generateToken(1L, "admin@medcare.com", "ADMIN");
        assertEquals("admin@medcare.com", jwtProvider.getEmailFromToken(token));
    }

    @Test
    void getRoleFromToken_shouldReturnCorrectRole() {
        String token = jwtProvider.generateToken(1L, "admin@medcare.com", "ADMIN");
        assertEquals("ADMIN", jwtProvider.getRoleFromToken(token));
    }

    @Test
    void getUserIdFromToken_shouldReturnCorrectId() {
        String token = jwtProvider.generateToken(42L, "test@medcare.com", "MEDECIN");
        assertEquals(42L, jwtProvider.getUserIdFromToken(token));
    }
}
