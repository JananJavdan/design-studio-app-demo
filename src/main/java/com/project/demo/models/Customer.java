package com.project.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User{

    @NotNull
    @Size(min = 5, max = 100)
    private String address;

    @Column(unique = true, nullable = false, length = 50)
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @Column(name = "failed_attempts")
    private int failedAttempts = 0;

    @Column(name = "account_locked")
    private boolean accountLocked = false;

    @NotNull
    @PastOrPresent
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "customer")
    private List<Design> designs;

    public Customer() {
        this.role = Role.CUSTOMER;
    }


}
