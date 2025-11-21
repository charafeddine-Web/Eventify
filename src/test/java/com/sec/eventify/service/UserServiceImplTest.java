package com.sec.eventify.service;

import com.sec.eventify.config.PasswordEncoderConfig;
import com.sec.eventify.exception.UsernameAlreadyExistsException;
import com.sec.eventify.model.User;
import com.sec.eventify.model.enums.UserRole;
import com.sec.eventify.repository.UserRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRespository userRepository;

    @Mock
    private PasswordEncoderConfig passwordEncoderConfig;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_shouldEncodePasswordAndSaveUser_whenEmailNotPresent() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("plain");
        user.setRole(UserRole.ROLE_USER);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoderConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode("plain")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.register(user);

        assertEquals("encoded", result.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void register_shouldThrow_whenEmailAlreadyExists() {
        User user = new User();
        user.setEmail("exists@example.com");
        user.setPassword("p");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.register(user));
        verify(userRepository, never()).save(any());
    }
}

