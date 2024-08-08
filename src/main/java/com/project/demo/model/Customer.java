package com.project.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Customer extends User{

    private String address;
    private Date registrationDate;


}
