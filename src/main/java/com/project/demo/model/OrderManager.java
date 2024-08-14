package com.project.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class OrderManager extends User{

    @OneToMany(mappedBy = "orderManager")
    private List<Order> orders;
    @Column
    private String orderDetails;
    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus orderStatus;

    public OrderManager() {
        this.setRole(Role.ORDER_MANAGER);
    }


}
