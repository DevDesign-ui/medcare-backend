package com.medcare.service;

import com.medcare.dto.AuthResponse;
import com.medcare.dto.LoginRequest;
import com.medcare.dto.RegisterRequest;
import com.medcare.entity.User;
import com.medcare.repository.UserRepository;
import com.medcare.security.JwtProvider;
import com.medcare.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setNom("Admin");
        testUser.setPrenom("System");
        testUser.setEmail("admin@medcare.com");
        testUser.setPassword("encodedPassword");
        testUser.setRole(com.medcare.entity.Role.ADMIN);
        testUser.setActive(true);
    }

    @Test
    void register_shouldCreateUserAndReturnToken() {
        RegisterRequest request = new RegisterRequest();
        request.setNom("Test");
        request.setPrenom("User");
        request.setEmail("test@test.com");
        request.setPassword("password123");
        request.setRole("ADMIN");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtProvider.generateToken(any(), anyString(), anyString())).thenReturn("jwt-token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("ADMIN", response.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_shouldReturnTokenWhenCredentialsValid() {
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@medcare.com");
        request.setPassword("admin123");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtProvider.generateToken(any(), anyString(), anyString())).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("ADMIN", response.getRole());
    }

    @Test
    void login_shouldThrowWhenUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmail("unknown@test.com");
        request.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(com.medcare.exception.InvalidCredentialsException.class,
                () -> authService.login(request));
    }

    @Test
    void login_shouldThrowWhenPasswordInvalid() {
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@medcare.com");
        request.setPassword("wrongpassword");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(com.medcare.exception.InvalidCredentialsException.class,
                () -> authService.login(request));
    }
}
