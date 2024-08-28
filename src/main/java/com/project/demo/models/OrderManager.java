package com.project.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@DiscriminatorValue("ORDER_MANAGER")
public class OrderManager extends User{

    @OneToMany(mappedBy = "orderManager")
    private List<Order> orders;
    @Column
    private String orderDetails;
    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus orderStatus;

    public OrderManager() {
        this.role = Role.ORDER_MANAGER;
    }


}
