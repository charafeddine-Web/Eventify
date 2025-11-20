package com.sec.eventify.controller;

import com.sec.eventify.model.Event;
import com.sec.eventify.security.CustomUserDetails;
import com.sec.eventify.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizer/events")
@RequiredArgsConstructor
public class EventController {
    @Autowired
    private final EventService eventService;

    @PostMapping
    public Event create(@RequestBody Event event, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return eventService.saveEvent(event, userDetails.getUser());
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody Event event, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return eventService.upadteEvent(event, id, userDetails.getUser());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        eventService.deleteEvent(id, userDetails.getUser());
    }

}