package com.example.commercepjt.dto.response;

import com.example.commercepjt.domain.OrderItem;

/**
 * 상품 장바구니 추가 완료 응답
 *
 * @param id
 * @param itemDto
 * @param itemQuantity
 */
public record OrderItemCreatedDto(
    Long id,
    ItemDto itemDto,
    int itemQuantity,

    int itemPrice
) {

    public OrderItemCreatedDto(OrderItem entity, String sellerName, int itemPrice) {
        this(entity.getId(), new ItemDto(entity.getItem(), sellerName), entity.getItemQuantity(),
            itemPrice);
    }

}
