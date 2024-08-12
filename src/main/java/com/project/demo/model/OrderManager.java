package com.project.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class OrderManager extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "orderManager")
    private List<Order> orders;

    private String orderDetails;
    private OrderStatus orderStatus;


}
