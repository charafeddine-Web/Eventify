package com.sec.eventify.repository;

import com.sec.eventify.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Long,Event> {
}
