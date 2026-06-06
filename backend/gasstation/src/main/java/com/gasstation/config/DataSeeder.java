package com.gasstation.config;

import com.gasstation.model.entity.User;
import com.gasstation.model.enums.UserRole;
import com.gasstation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;

@Configuration
public class DataSeeder {
    @Bean
    public CommandLineRunner seedAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByEmail("admin@gas.com").isEmpty()) {
                User admin = new User("admin@gas.com", encoder.encode("admin123"),
                        "System Administrator", UserRole.ADMIN, OffsetDateTime.now());
                userRepository.save(admin);
                System.out.println("Seeded default admin: admin@gas.com / admin123");
            }
        };
    }
}
