package com.example.commercepjt.repository;

import com.example.commercepjt.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
