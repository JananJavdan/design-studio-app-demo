package com.project.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@DiscriminatorValue("DESIGN_MANAGER")
public class DesignManager extends User{

    @OneToMany(mappedBy = "designManager")
    private List<Design> designs;

    public DesignManager() {
        this.role = Role.DESIGN_MANAGER;
    }

}
