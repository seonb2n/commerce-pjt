package com.example.commercepjt.dto.response;

import com.example.commercepjt.common.enums.DeliveryStatus;
import com.example.commercepjt.domain.OrderItem;

public record OrderItemProgressDto(
    Long id,
    Long itemId,
    int itemQuantity,
    int itemPrice,
    DeliveryStatus deliveryStatus
) {

    public OrderItemProgressDto(OrderItem entity) {
        this(entity.getId(), entity.getItem().getId(), entity.getItemQuantity(),
            entity.getPurchasedItemPrice(), entity.getDeliveryStatus());
    }
}
