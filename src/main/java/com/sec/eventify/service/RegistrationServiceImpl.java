package com.sec.eventify.service;

import com.sec.eventify.exception.EventNotFoundExecption;
import com.sec.eventify.model.Event;
import com.sec.eventify.model.Registration;
import com.sec.eventify.model.User;
import com.sec.eventify.repository.EventRepository;
import com.sec.eventify.repository.RegistrationRepository;
import com.sec.eventify.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private final UserRespository userRepository;
    @Autowired
    private final EventRepository eventRepository;
    @Autowired
    private final RegistrationRepository registrationRepository;

    @Override
    public Registration registerEvent(Long eventId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundExecption("Événement non trouvé : " + eventId));

        Registration registration = Registration.builder()
                .user(user)
                .event(event)
                .registeredAt(LocalDateTime.now())
                .status("CONFIRMED")
                .build();

        return registrationRepository.save(registration);
    }

    @Override
    public List<Registration> getRegistrationsByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return registrationRepository.findByUserId(user.getId());
    }
}