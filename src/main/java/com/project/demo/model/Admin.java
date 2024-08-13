package com.project.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Admin() {
        this.setRole(Role.ADMIN);
    }


}
