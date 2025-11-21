package com.sec.eventify.service;

import com.sec.eventify.model.Event;
import com.sec.eventify.model.User;
import com.sec.eventify.model.enums.UserRole;
import com.sec.eventify.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void saveEvent_shouldSetDateTimeAndOrganizerAndSave() {
        Event event = new Event();
        User organizer = new User();
        organizer.setId(1L);

        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event result = eventService.saveEvent(event, organizer);

        assertNotNull(result.getDateTime());
        assertEquals(organizer, result.getOrganizer());
        verify(eventRepository).save(event);
    }

    @Test
    void upadteEvent_shouldThrow_whenEventNotFound() {
        Event event = new Event();
        event.setId(2L);
        User organizer = new User();
        organizer.setId(1L);

        when(eventRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.upadteEvent(event, 2L, organizer));
    }

    @Test
    void upadteEvent_shouldThrow_whenOrganizerMismatch() {
        Event existing = new Event();
        User existingOrganizer = new User();
        existingOrganizer.setId(5L);
        existing.setOrganizer(existingOrganizer);
        existing.setId(3L);

        Event toUpdate = new Event();
        User organizer = new User();
        organizer.setId(2L);

        when(eventRepository.findById(3L)).thenReturn(Optional.of(existing));

        assertThrows(RuntimeException.class, () -> eventService.upadteEvent(toUpdate, 3L, organizer));
    }
}

