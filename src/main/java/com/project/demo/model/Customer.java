package com.project.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class Customer extends User{

    @NotNull
    @Size(min = 5, max = 100)
    private String address;

    @Column(name = "failed_attempts")
    private int failedAttempts = 0;

    @Column(name = "account_locked")
    private boolean accountLocked = false;

    @NotNull
    @PastOrPresent
    private LocalDate registrationDate;

    public Customer() {
        this.setRole(Role.CUSTOMER);
    }


}
