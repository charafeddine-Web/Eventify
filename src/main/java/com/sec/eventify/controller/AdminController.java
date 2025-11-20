package com.sec.eventify.controller;

import com.sec.eventify.model.User;
import com.sec.eventify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final UserService userService;

    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PutMapping("/users/{id}/role")
    public User changeRole(@PathVariable Long id, @RequestParam String role) {
        return userService.changeRole(id, role);
    }

}
