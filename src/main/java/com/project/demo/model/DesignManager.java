package com.project.demo.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class DesignManager extends User{
    private String expertise;
}