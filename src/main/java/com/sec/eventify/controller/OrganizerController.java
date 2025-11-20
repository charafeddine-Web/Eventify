package com.sec.eventify.controller;

import com.sec.eventify.model.Event;
import com.sec.eventify.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizer/events")
@RequiredArgsConstructor
public class OrganizerController {

    private final EventRepository repo;

    @PostMapping
    public Event create(@RequestBody Event event) {
        return repo.save(event);
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody Event event) {
        event.setId(id);
        return repo.save(event);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
