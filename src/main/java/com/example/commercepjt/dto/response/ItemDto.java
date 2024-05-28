package com.example.commercepjt.dto.response;

import com.example.commercepjt.domain.Item;

public record ItemDto(
    Long id,
    String userSellerName,
    String name,
    String description,
    int price,
    boolean isSelling
) {

    public ItemDto(Item entity, String sellerName) {
        this(entity.getId(), sellerName, entity.getName(), entity.getDescription(),
            entity.getPrice(), entity.isSelling());
    }

    public ItemDto(Item entity) {
        this(entity.getId(), null, entity.getName(), entity.getDescription(),
            entity.getPrice(), entity.isSelling());
    }
}
