package com.project.demo.Services;

import com.project.demo.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order placeOrder(Order order);
    List<Order> getAllOrders();
    Optional<Order> getOrderById(Long id);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);

}
