package com.project.demo.repositories;

import com.project.demo.models.OrderManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderManagerRepository extends JpaRepository<OrderManager, Long> {
}
