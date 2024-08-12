package com.project.demo.Services;

import com.project.demo.model.Role;
import com.project.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    boolean login(String email, String password);
    Role getRole(Long id);

}
