package com.sec.eventify.service;

import com.sec.eventify.model.User;

import java.util.List;

public interface UserService {
    User register(User user);
    User changeRole(Long id, String newRole);
    List<User> getAll();
    User findByEmail(String email);
}
