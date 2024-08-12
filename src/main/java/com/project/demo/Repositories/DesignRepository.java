package com.project.demo.Repositories;

import com.project.demo.model.Design;
import com.project.demo.model.DesignManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignRepository extends JpaRepository<Design, Long> {
    List<Design> findByDesignManager(DesignManager designManager);
}
