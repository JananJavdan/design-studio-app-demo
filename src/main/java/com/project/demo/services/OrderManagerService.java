package com.project.demo.services;

import com.project.demo.models.OrderManager;

import java.util.List;

public interface OrderManagerService {
    OrderManager createOrderManager(OrderManager orderManager);
    List<OrderManager> getAllOrderManagers();
    OrderManager updateOrderManager(Long id, OrderManager orderManager);
    void deleteOrderManager(Long id);
}
