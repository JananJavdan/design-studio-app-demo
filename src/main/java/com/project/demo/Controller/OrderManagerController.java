package com.project.demo.Controller;

import com.project.demo.Services.OrderService;
import com.project.demo.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class OrderManagerController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id).orElseThrow(() -> new RuntimeException("Order not found")));
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody Order order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/process-order/{id}")
    public ResponseEntity<Void> processOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("Processed");
        orderService.updateOrder(id, order);
        return ResponseEntity.noContent().build();
    }
}
