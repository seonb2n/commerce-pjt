package com.example.commercepjt.dto.response;

import com.example.commercepjt.domain.OrderItem;

/**
 * 상품 장바구니 추가 완료 응답
 */
public record OrderItemCompleteDto(
    Long id,
    Long itemId,
    int itemQuantity,

    int itemPrice
) {

    public OrderItemCompleteDto(OrderItem entity) {
        this(entity.getId(), entity.getItem().getId(), entity.getItemQuantity(),
            entity.getPurchasedItemPrice());
    }

}
