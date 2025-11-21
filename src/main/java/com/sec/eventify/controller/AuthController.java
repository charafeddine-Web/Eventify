package com.sec.eventify.controller;

import com.sec.eventify.config.SecurityConfig;
import com.sec.eventify.model.User;
import com.sec.eventify.model.enums.UserRole;
import com.sec.eventify.repository.UserRespository;
import com.sec.eventify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserService userService;

    @PostMapping("/users")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
}
