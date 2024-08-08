package com.project.demo.Services;

import com.project.demo.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
    User getUserByUsername(String username);
}
