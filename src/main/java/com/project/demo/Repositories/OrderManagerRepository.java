package com.project.demo.Repositories;

import com.project.demo.model.OrderManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderManagerRepository extends JpaRepository<OrderManager, Long> {
}
