package com.project.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class DesignManager extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "designManager")
    private List<Design> designs;

    public DesignManager() {
        this.setRole(Role.DESIGN_MANAGER);
    }

}
