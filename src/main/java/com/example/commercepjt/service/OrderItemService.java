package com.example.commercepjt.service;

import com.example.commercepjt.common.enums.DeliveryStatus;
import com.example.commercepjt.domain.OrderItem;
import com.example.commercepjt.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public OrderItem getOrderItemById(long id) {
        return orderItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<OrderItem> findAllItemsByBuyerId(long buyerId) {
        return orderItemRepository.findAllByUserBuyerId(buyerId);
    }

    public OrderItem updateOrderItemDeliveryStatus(long orderItemId, long buyerId,
        DeliveryStatus targetStatus) {
        OrderItem item = orderItemRepository.findByIdAndUserBuyerId(orderItemId, buyerId);
        item.setDeliveryStatus(targetStatus);
        return item;
    }
}
