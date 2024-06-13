package com.example.commercepjt.dto.request;

public record ItemCreateDto(
    Long categoryId,
    String name,
    String description,
    int price,
    int stockQuantity
) {
}
