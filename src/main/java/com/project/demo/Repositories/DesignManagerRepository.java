package com.project.demo.Repositories;

import com.project.demo.model.DesignManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignManagerRepository extends JpaRepository<DesignManager, Long> {
}
