package com.project.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private float totalPrice;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "design_id", referencedColumnName = "id")
    private Design design;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "order_manager_id", referencedColumnName = "id")
    private OrderManager orderManager;
}
