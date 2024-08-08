package com.project.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected String email;
    protected String password;
    protected String phoneNumber;
    @Enumerated(EnumType.STRING)
    protected Role role;
}
