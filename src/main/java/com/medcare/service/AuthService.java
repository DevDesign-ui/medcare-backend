package com.medcare.service;

import com.medcare.dto.AuthResponse;
import com.medcare.dto.LoginRequest;
import com.medcare.dto.RegisterRequest;
import com.medcare.dto.UserDTO;

import java.util.List;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
}
