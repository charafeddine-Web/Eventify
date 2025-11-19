package com.sec.eventify.repository;

import com.sec.eventify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {
}
