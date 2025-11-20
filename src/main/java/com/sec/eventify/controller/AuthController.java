package com.sec.eventify.controller;

import com.sec.eventify.model.User;
import com.sec.eventify.model.enums.UserRole;
import com.sec.eventify.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class AuthController {

    private final UserRespository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/users")
    public User register(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.ROLE_USER);

        return userRepository.save(user);
    }
}
