package com.sec.eventify.service;

import com.sec.eventify.exception.EventNotFoundExecption;
import com.sec.eventify.model.Event;
import com.sec.eventify.model.Registration;
import com.sec.eventify.model.User;
import com.sec.eventify.repository.EventRepository;
import com.sec.eventify.repository.RegistrationRepository;
import com.sec.eventify.repository.UserRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    @Mock
    private UserRespository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    void registerEvent_shouldSaveRegistration_whenUserAndEventExist() {
        User user = new User();
        user.setId(10L);
        user.setEmail("u@example.com");

        Event event = new Event();
        event.setId(20L);

        when(userRepository.findByEmail("u@example.com")).thenReturn(Optional.of(user));
        when(eventRepository.findById(20L)).thenReturn(Optional.of(event));
        when(registrationRepository.save(any(Registration.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Registration reg = registrationService.registerEvent(20L, "u@example.com");

        assertEquals(user, reg.getUser());
        assertEquals(event, reg.getEvent());
        assertNotNull(reg.getRegisteredAt());
        assertEquals("CONFIRMED", reg.getStatus());
        verify(registrationRepository).save(any(Registration.class));
    }

    @Test
    void registerEvent_shouldThrow_whenEventNotFound() {
        User user = new User();
        user.setId(10L);
        user.setEmail("u@example.com");

        when(userRepository.findByEmail("u@example.com")).thenReturn(Optional.of(user));
        when(eventRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundExecption.class, () -> registrationService.registerEvent(99L, "u@example.com"));
    }

    @Test
    void registerEvent_shouldThrow_whenUserNotFound() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> registrationService.registerEvent(1L, "missing@example.com"));
    }
}

