package com.gasstation.controller;

import com.gasstation.model.dto.auth.CreateUserRequest;
import com.gasstation.model.dto.auth.UserDto;
import com.gasstation.model.entity.User;
import com.gasstation.model.enums.UserRole;
import com.gasstation.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(@RequestParam(required = false) UserRole role) {
        List<UserDto> users = userService.getAllUsers(role).stream()
                .map(userService::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.toDto(userService.getUserById(id)));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreateUserRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        UserRole role = request.getRole() == null ? UserRole.WORKER : request.getRole();
        User user = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()),
                request.getFullName(), role, OffsetDateTime.now());
        return ResponseEntity.ok(userService.toDto(userService.saveUser(user)));
    }

    @PutMapping("/{id}/ban")
    public ResponseEntity<UserDto> setBan(@PathVariable UUID id, @RequestParam boolean banned) {
        User user = userService.getUserById(id);
        user.setBanned(banned);
        return ResponseEntity.ok(userService.toDto(userService.saveUser(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
