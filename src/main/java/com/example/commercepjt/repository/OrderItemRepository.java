package com.example.commercepjt.repository;

import com.example.commercepjt.domain.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @EntityGraph(attributePaths = {"item"})
    List<OrderItem> findAllByUserBuyerId(Long userBuyerId);

}
