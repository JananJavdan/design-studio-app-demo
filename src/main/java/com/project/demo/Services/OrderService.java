package com.project.demo.Services;

import com.project.demo.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);
    List<Order> getAllOrders();
    Order updateOrderStatus(Long id, Order order);
    void cancelOrder(Long id);
}
