package com.example.commercepjt.service;

import com.example.commercepjt.domain.OrderItem;
import com.example.commercepjt.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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


}
