package com.project.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DesignManager extends User{
    private String expertise;

}
