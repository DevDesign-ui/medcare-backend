package com.medcare.service.impl;

import com.medcare.dto.AuthResponse;
import com.medcare.dto.LoginRequest;
import com.medcare.dto.RegisterRequest;
import com.medcare.dto.UserDTO;
import com.medcare.entity.Role;
import com.medcare.entity.User;
import com.medcare.exception.BusinessException;
import com.medcare.exception.InvalidCredentialsException;
import com.medcare.exception.ResourceAlreadyExistsException;
import com.medcare.exception.ResourceNotFoundException;
import com.medcare.mapper.UserMapper;
import com.medcare.repository.UserRepository;
import com.medcare.security.JwtProvider;
import com.medcare.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Un utilisateur avec cet email existe deja");
        }

        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (Exception e) {
            throw new BusinessException("Role invalide. Roles valides : ADMIN, MEDECIN, SECRETAIRE, PHARMACIEN");
        }

        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setTelephone(request.getTelephone());
        user.setRole(role);
        user.setActive(true);

        User saved = userRepository.save(user);
        String token = jwtProvider.generateToken(saved.getId(), saved.getEmail(), saved.getRole().name());

        return new AuthResponse(token, saved.getRole().name());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Email ou mot de passe invalide"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Email ou mot de passe invalide");
        }

        if (user.getActive() != null && !user.getActive()) {
            throw new InvalidCredentialsException("Compte desactive. Contactez l'administrateur");
        }

        String token = jwtProvider.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getRole().name());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouve avec l'id : " + id));
        return UserMapper.toDTO(user);
    }
}
