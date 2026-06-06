package com.gasstation.service;

import com.gasstation.exceptions.NotFoundException;
import com.gasstation.model.dto.auth.UserDto;
import com.gasstation.model.entity.User;
import com.gasstation.model.enums.UserRole;
import com.gasstation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    protected UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto toDto(User u) {
        return new UserDto(u.getUserId(), u.getEmail(), u.getFullName(), u.getRole(), u.isBanned(), u.getCreatedAt());
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<User> getAllUsers(UserRole role) {
        return role == null ? userRepository.findAll() : userRepository.findByRole(role);
    }

    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("User not found");
        userRepository.deleteById(userId);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
