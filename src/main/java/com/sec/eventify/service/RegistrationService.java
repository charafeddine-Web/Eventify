package com.sec.eventify.service;

import com.sec.eventify.model.Registration;

import java.util.List;

public interface RegistrationService {
    Registration registerEvent(Long eventId, String userEmail);
    List<Registration> getRegistrationsByEmail(String userEmail);
}
