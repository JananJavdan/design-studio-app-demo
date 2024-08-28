package com.project.demo.Service;

import com.project.demo.repositories.UserRepository;
import com.project.demo.services.UserServiceImpl;
import com.project.demo.models.Role;
import com.project.demo.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        // Arrange
        User user = new User() {}; // Anonymous subclass to instantiate abstract User
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User registeredUser = userService.registerUser(user);

        // Assert
        assertEquals(user.getEmail(), registeredUser.getEmail());
        assertEquals("encodedPassword", registeredUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void registerUser_UserAlreadyExists() {
        // Arrange
        User user = new User() {};
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> userService.registerUser(user));
        assertEquals("User with email test@example.com already exists", exception.getMessage());
    }

    @Test
    void getUserByEmail_Success() {
        // Arrange
        User user = new User() {};
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.getUserByEmail("test@example.com");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void getUserByEmail_NotFound() {
        // Arrange
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<User> foundUser = userService.getUserByEmail("test@example.com");

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    void login_Success() {
        // Arrange
        User user = new User() {};
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", user.getPassword())).thenReturn(true);

    }

    @Test
    void login_Failure() {
        // Arrange
        User user = new User() {};
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", user.getPassword())).thenReturn(false);

    }

    @Test
    void getRole_Success_Admin() {
        // Arrange
        User user = new User() {};  // Anonymous subclass to instantiate abstract User
        user.setRole(Role.ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Role role = userService.getRole(1L);

        // Assert
        assertEquals(Role.ADMIN, role);
    }

    @Test
    void getRole_UserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> userService.getRole(1L));
        assertEquals("User not found", exception.getMessage());
    }
}
