package com.project.demo.Services;

import com.project.demo.model.Admin;
import com.project.demo.model.Order;
import com.project.demo.model.User;

import java.util.List;

public interface AdminService {
    void login(String email, String password);
    List<User> manageUsers();
    List<Order> manageOrders();
    void updateProductCatalog(Long productId, String productDetails);
    void sendConfirmationEmail(Long userId, String message);
    void sendNotificationEmail(Long userId, String message);
}
