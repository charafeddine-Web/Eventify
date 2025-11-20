package com.sec.eventify.controller;

import com.sec.eventify.config.SecurityConfig;
import com.sec.eventify.model.User;
import com.sec.eventify.model.enums.UserRole;
import com.sec.eventify.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserRespository userRepository;
    @Autowired
    private final SecurityConfig sec;

    @PostMapping("/users")
    public User register(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(sec.passwordEncoder().encode(user.getPassword()));
        user.setRole(UserRole.ROLE_USER);

        return userRepository.save(user);
    }
}
