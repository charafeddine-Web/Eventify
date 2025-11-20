package com.sec.eventify.service;

import com.sec.eventify.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Event saveEvent(Event event);
    Event upadteEvent(Event event);
    void deleteEvent(Long eventId);
     Optional<Event> getEventById(Long eventId);
     List<Event> getAllEvents();


}
