package com.sec.eventify.service;

import com.sec.eventify.config.PasswordEncoderConfig;
import com.sec.eventify.exception.UsernameAlreadyExistsException;
import com.sec.eventify.model.User;
import com.sec.eventify.model.enums.UserRole;
import com.sec.eventify.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRespository userRepository;
    @Autowired
    private PasswordEncoderConfig passwordEncoder;

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UsernameAlreadyExistsException("Email déjà utilisé : " + user.getEmail());
        }

        user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
        user.setRole(user.getRole());
        return userRepository.save(user);
    }

    @Override
    public User changeRole(Long userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        try {
            user.setRole(UserRole.valueOf(newRole));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Rôle invalide : " + newRole);
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}