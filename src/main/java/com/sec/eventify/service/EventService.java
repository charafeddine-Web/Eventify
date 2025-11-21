package com.sec.eventify.service;

import com.sec.eventify.model.Event;
import com.sec.eventify.model.User;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Event saveEvent(Event event, User organizer);
    Event upadteEvent(Event event, Long id, User organizer);
    void deleteEvent(Long eventId, User organizer);
    Optional<Event> getEventById(Long eventId);
    List<Event> getAllEvents();


}