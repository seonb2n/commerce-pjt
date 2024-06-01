package com.example.commercepjt.dto.request;

public record ItemCreateDto(
    Long sellerId,
    Long categoryId,
    String name,
    String description,
    int price,
    int stockQuantity
) {
}
