package com.example.commercepjt.repository;

import com.example.commercepjt.common.enums.DeliveryStatus;
import com.example.commercepjt.domain.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @EntityGraph(attributePaths = {"item"})
    List<OrderItem> findAllByUserBuyerIdAndDeliveryStatus(Long userBuyerId, DeliveryStatus deliveryStatus);

    int deleteAllByUserBuyerIdAndDeliveryStatus(Long userBuyerId, DeliveryStatus deliveryStatus);

    OrderItem findByIdAndUserBuyerId(Long id, Long userBuyerId);

    @EntityGraph(attributePaths = {"order"})
    @Query("SELECT oi from OrderItem oi join oi.item i where i.userSeller.id = :sellerId")
    List<OrderItem> findAllBySellerId(@Param("sellerId") Long sellerId);
}
