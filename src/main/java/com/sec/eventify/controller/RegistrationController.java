package com.sec.eventify.controller;

import com.sec.eventify.model.Registration;
import com.sec.eventify.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/events/{id}/register")
    public Registration registerEvent(@PathVariable Long id,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        return registrationService.registerEvent(id, userDetails.getUsername());
    }

    @GetMapping("/registrations")
    public List<Registration> getMyRegistrations(Authentication authentication) {

        String userEmail = authentication.getName();

        return registrationService.getRegistrationsByEmail(userEmail);
    }
}
