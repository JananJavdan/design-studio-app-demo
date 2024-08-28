package com.project.demo.services;

import com.project.demo.models.Role;
import com.project.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> login(String email, String password);
    Role getRole(Long id);
    Optional<User> confirmUser(String token);

}
