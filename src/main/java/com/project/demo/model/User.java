package com.project.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(unique = true, nullable = false, length = 50)
    @NotNull
    @Size(min = 3, max = 50)
    protected String name;

    @Column(unique = true, nullable = false, length = 100)
    @NotNull
    protected String email;

    @Column(nullable = false)
    @NotNull
    @Size(min = 8, max = 100)
    protected String password;

    @Column(nullable = false)
    @NotNull
    protected String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", insertable = false, updatable = false)
    @NotNull
    protected Role role;

    @Column(nullable = false)
    protected boolean accountLocked = false;

    @Column(nullable = false)
    protected int failedAttempts = 0;

}
