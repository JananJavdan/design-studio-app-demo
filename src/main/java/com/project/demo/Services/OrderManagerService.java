package com.project.demo.Services;

import com.project.demo.model.OrderManager;

import java.util.List;

public interface OrderManagerService {
    OrderManager createOrderManager(OrderManager orderManager);
    List<OrderManager> getAllOrderManagers();
    OrderManager updateOrderManager(Long id, OrderManager orderManager);
    void deleteOrderManager(Long id);
}
