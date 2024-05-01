package com.example.commercepjt.dto.response;

import com.example.commercepjt.domain.Item;
import com.example.commercepjt.domain.OrderItem;

/**
 * 상품 장바구니 추가 완료 응답
 *
 * @param id
 * @param itemId
 * @param itemName
 * @param itemQuantity
 * @param itemPrice
 */
public record OrderItemCreatedDto(
    Long id,
    Long itemId,

    String itemName,

    int itemQuantity,

    int itemPrice
) {

    public OrderItemCreatedDto(OrderItem entity) {
        this(entity.getId(), entity.getItem().getId(), entity.getItem().getName(), entity.getItemQuantity(),
            entity.getItem().getPrice());
    }

}
