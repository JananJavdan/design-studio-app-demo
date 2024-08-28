package com.project.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User{

    public Admin() {
        this.role = Role.ADMIN;
    }


}
