package com.medcare.config;

import com.medcare.entity.Role;
import com.medcare.entity.User;
import com.medcare.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@medcare.com")) {
                User admin = new User("Admin", "System", "admin@medcare.com",
                        passwordEncoder.encode("admin123"), "770000000", Role.ADMIN);
                userRepository.save(admin);
            }
            if (!userRepository.existsByEmail("medecin@medcare.com")) {
                User medecin = new User("Diop", "Aminata", "medecin@medcare.com",
                        passwordEncoder.encode("medecin123"), "771111111", Role.MEDECIN);
                userRepository.save(medecin);
            }
            if (!userRepository.existsByEmail("secretaire@medcare.com")) {
                User secretaire = new User("Fall", "Mamadou", "secretaire@medcare.com",
                        passwordEncoder.encode("secret123"), "772222222", Role.SECRETAIRE);
                userRepository.save(secretaire);
            }
            if (!userRepository.existsByEmail("pharmacien@medcare.com")) {
                User pharmacien = new User("Ndiaye", "Awa", "pharmacien@medcare.com",
                        passwordEncoder.encode("pharma123"), "773333333", Role.PHARMACIEN);
                userRepository.save(pharmacien);
            }
        };
    }
}
