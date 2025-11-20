package com.sec.eventify.controller;

import com.sec.eventify.model.Registration;
import com.sec.eventify.model.User;
import com.sec.eventify.model.Event;
import com.sec.eventify.repository.RegistrationRepository;
import com.sec.eventify.repository.EventRepository;
import com.sec.eventify.repository.UserRespository;
import com.sec.eventify.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
}
