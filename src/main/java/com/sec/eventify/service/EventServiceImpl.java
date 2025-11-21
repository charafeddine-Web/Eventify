package com.sec.eventify.service;

import com.sec.eventify.model.Event;
import com.sec.eventify.model.User;
import com.sec.eventify.model.enums.UserRole;
import com.sec.eventify.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl  implements EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event, User organizer) {
        event.setDateTime(LocalDateTime.now());
        event.setOrganizer(organizer);
        return eventRepository.save(event);
    };

    public Event upadteEvent(Event event, Long id, User organizer) {
        event.setId(id);
        event.setDateTime(LocalDateTime.now());
        event.setOrganizer(organizer);

        Event existing = eventRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!existing.getOrganizer().getId().equals(organizer.getId())) {
            throw new RuntimeException("You can only update your own events");
        }

        return eventRepository.save(event);
    };
    public void deleteEvent(Long eventId, User user){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (user.getRole() == UserRole.ROLE_ADMIN) {
            eventRepository.delete(event);
            return;
        }

        if (!event.getOrganizer().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own events");
        }

        eventRepository.deleteById(eventId);
    };
    public Optional<Event> getEventById(Long eventId){
        return eventRepository.findById(eventId);
    };
    public List<Event> getAllEvents(){
        return  eventRepository.findAll();
    };
}