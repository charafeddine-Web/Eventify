package com.sec.eventify.controller;

import com.sec.eventify.model.Event;
import com.sec.eventify.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/events")
@RequiredArgsConstructor
public class EventController {
    private final EventRepository repo;

    @GetMapping
    public List<Event> all() {
        return repo.findAll();
    }
}
