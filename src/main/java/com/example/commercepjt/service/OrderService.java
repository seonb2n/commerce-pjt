package com.example.commercepjt.service;

import com.example.commercepjt.domain.Order;
import com.example.commercepjt.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    }
}
