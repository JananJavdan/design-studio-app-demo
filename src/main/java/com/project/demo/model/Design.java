package com.project.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
@Data
@Entity
public class Design {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotNull
    private String name;

    @Column(nullable = true, length = 100)
    private String category;

    @Column(nullable = true, length = 50)
    private String color;

    @Column(nullable = true, length = 10)
    private String size;

    @Column(nullable = true, length = 255)
    private String logo;

    @NotBlank(message = "Text cannot be blank")
    @Size(max = 255, message = "Text cannot exceed 255 characters")
    private String text;

    @NotBlank(message = "Logo position cannot be blank")
    @Size(max = 50, message = "Logo position cannot exceed 50 characters")
    private String logoPosition;

    @NotBlank(message = "Font cannot be blank")
    @Size(max = 100, message = "Font cannot exceed 100 characters")
    private String font;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "designer_id", referencedColumnName = "id", nullable = false)
    private DesignManager designManager;




}
