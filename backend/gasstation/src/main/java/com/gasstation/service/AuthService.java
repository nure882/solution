package com.gasstation.service;

import com.gasstation.model.dto.auth.AuthResponse;
import com.gasstation.model.dto.auth.LoginRequest;
import com.gasstation.model.dto.auth.RegisterRequest;
import com.gasstation.model.entity.User;
import com.gasstation.model.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    protected AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse registerUser(RegisterRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName(),
                UserRole.CUSTOMER,
                OffsetDateTime.now()
        );
        user = userService.saveUser(user);
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(user.getUserId(), token, user.getEmail(), user.getFullName(), user.getRole());
    }

    public AuthResponse loginUser(LoginRequest request) {
        User user = userService.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (user.isBanned()) {
            throw new BadCredentialsException("Account is banned");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(user.getUserId(), token, user.getEmail(), user.getFullName(), user.getRole());
    }
}
