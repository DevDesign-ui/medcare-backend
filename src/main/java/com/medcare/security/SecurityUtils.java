package com.medcare.security;

import com.medcare.entity.User;
import com.medcare.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private final UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails)) {
            return null;
        }
        org.springframework.security.core.userdetails.UserDetails userDetails =
                (org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername()).orElse(null);
    }

    public String getCurrentUserRole() {
        User user = getCurrentUser();
        return user != null ? user.getRole().name() : null;
    }
}
