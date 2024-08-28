package com.project.demo.repositories;

import com.project.demo.models.Design;
import com.project.demo.models.DesignManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignRepository extends JpaRepository<Design, Long> {
    List<Design> findByDesignManager(DesignManager designManager);
}
