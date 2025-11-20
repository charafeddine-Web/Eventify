package com.sec.eventify.service;

import com.sec.eventify.model.Event;
import com.sec.eventify.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl  implements EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event){
        return eventRepository.save(event);
    };
    public Event upadteEvent(Event event){
        return eventRepository.save(event);
    };
    public void deleteEvent(Long eventId){
        eventRepository.deleteById(eventId);
    };
    public Optional<Event> getEventById(Long eventId){
        return eventRepository.findById(eventId);
    };
    public List<Event> getAllEvents(){
        return  eventRepository.findAll();
    };
}