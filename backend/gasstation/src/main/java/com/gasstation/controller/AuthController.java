package com.gasstation.controller;

import com.gasstation.model.dto.auth.AuthResponse;
import com.gasstation.model.dto.auth.LoginRequest;
import com.gasstation.model.dto.auth.RegisterRequest;
import com.gasstation.model.dto.auth.UserDto;
import com.gasstation.model.entity.CustomUserDetails;
import com.gasstation.service.AuthService;
import com.gasstation.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userService.toDto(details.getUser()));
    }
}
