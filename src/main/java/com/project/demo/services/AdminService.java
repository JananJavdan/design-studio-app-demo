package com.project.demo.services;

import com.project.demo.models.User;

import java.util.List;

public interface AdminService {
    void login(String email, String password);
    User registerAdmin(User admin);
    List<User> manageUsers();
    User createUser(User user);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
    void sendConfirmationEmail(Long userId, String message);
    void sendNotificationEmail(Long userId, String message);
    User viewUserDetails(Long userId);
    void unlockAccount(Long userId);
}
