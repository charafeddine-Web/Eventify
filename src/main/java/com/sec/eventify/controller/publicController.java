package com.sec.eventify.controller;

import com.sec.eventify.model.Event;
import com.sec.eventify.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/public")
@RequiredArgsConstructor
public class publicController {

    @Autowired
    private final EventRepository repo;

    @GetMapping("/events")
    public List<Event> all() {
        return repo.findAll();
    }

}
