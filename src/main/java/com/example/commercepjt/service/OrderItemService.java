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

    public List<OrderItem> findAllItemsByBuyerIdAndStatusIsInBag(long buyerId) {
        return orderItemRepository.findAllByUserBuyerIdAndDeliveryStatus(buyerId,
            DeliveryStatus.IN_BAG);
    }

    public int clearAllItemsInBag(long buyerId) {
        return orderItemRepository.deleteAllByUserBuyerIdAndDeliveryStatus(buyerId,
            DeliveryStatus.IN_BAG);
    }

    public OrderItem updateOrderItemDeliveryStatus(long orderItemId, long buyerId,
        DeliveryStatus targetStatus) {
        OrderItem item = orderItemRepository.findByIdAndUserBuyerId(orderItemId, buyerId);
        item.setDeliveryStatus(targetStatus);
        return item;
    }

    public int getOrderItemPurchasedPriceSummary(long sellerId) {
        List<OrderItem> orderItemList = orderItemRepository.findAllBySellerId(sellerId);
        List<OrderItem> soldOrderItemList = orderItemList.stream()
            .filter((orderItem -> orderItem.getOrder().getDeliveryStatus() == DeliveryStatus.DONE))
            .toList();
        return soldOrderItemList.stream().mapToInt(OrderItem::getPurchasedItemPrice).sum();
    }
}
