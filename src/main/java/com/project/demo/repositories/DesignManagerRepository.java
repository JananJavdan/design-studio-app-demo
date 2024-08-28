package com.project.demo.repositories;

import com.project.demo.models.DesignManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignManagerRepository extends JpaRepository<DesignManager, Long> {
}
