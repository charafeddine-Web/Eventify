package com.sec.eventify.controller;

import com.sec.eventify.model.User;
import com.sec.eventify.security.CustomUserDetails;
import com.sec.eventify.service.EventService;
import com.sec.eventify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final EventService eventService;

    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PutMapping("/users/{id}/role")
    public User changeRole(@PathVariable Long id, @RequestParam String role) {
        return userService.changeRole(id, role);
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        eventService.deleteEvent(id, userDetails.getUser());
    }

}
