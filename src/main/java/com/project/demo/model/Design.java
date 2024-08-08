package com.project.demo.model;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
public class Design {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private String color;
    private String size;
    private String logo;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "designer_id", referencedColumnName = "id")
    private DesignManager designManager;


}
