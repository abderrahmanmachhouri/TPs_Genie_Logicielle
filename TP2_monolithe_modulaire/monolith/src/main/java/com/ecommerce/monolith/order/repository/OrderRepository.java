package com.ecommerce.monolith.order.repository;

import com.ecommerce.monolith.order.model.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<order, Long> {
    List<order> findByCustomerId(Long customerId);
}