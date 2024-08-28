package com.project.demo.services;

import com.project.demo.repositories.OrderManagerRepository;
import com.project.demo.models.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderManagerServiceImpl implements OrderManagerService{

    private OrderManagerRepository orderManagerRepository;
    @Autowired
    public OrderManagerServiceImpl(OrderManagerRepository orderManagerRepository){
        this.orderManagerRepository = orderManagerRepository;
    }

    @Override
    public OrderManager createOrderManager(OrderManager orderManager) {
        return orderManagerRepository.save(orderManager);
    }

    @Override
    public List<OrderManager> getAllOrderManagers() {
        return orderManagerRepository.findAll();
    }

    @Override
    public OrderManager updateOrderManager(Long id, OrderManager orderManager) {
        orderManager.setId(id);
        return orderManagerRepository.save(orderManager);
    }

    @Override
    public void deleteOrderManager(Long id) {
        orderManagerRepository.deleteById(id);

    }
}
