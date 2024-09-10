package com.project.demo.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "role")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = Customer.class, name = "CUSTOMER"),
        @JsonSubTypes.Type(value = DesignManager.class, name = "DESIGN_MANAGER"),
        @JsonSubTypes.Type(value = OrderManager.class, name = "ORDER_MANAGER"),
})
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

    private String confirmationToken;

    private boolean isEnabled = false;

    private String resetToken;

    public User() {
    }
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

