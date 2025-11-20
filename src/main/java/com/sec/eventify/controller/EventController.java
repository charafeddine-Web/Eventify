package com.sec.eventify.controller;

import com.sec.eventify.model.Event;
import com.sec.eventify.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizer/events")
@RequiredArgsConstructor
public class EventController {
    @Autowired
    private final EventService eventService;

    @PostMapping
    public Event create(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody Event event) {
        event.setId(id);
        return eventService.upadteEvent(event);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

}
