package com.example.commercepjt.dto.response;

import com.example.commercepjt.common.enums.DeliveryStatus;
import com.example.commercepjt.common.enums.PaymentStatus;
import com.example.commercepjt.domain.Order;
import java.util.List;

public record OrderProgressDto(
    Long id,
    List<OrderItemCompleteDto> orderItemCompleteDtoList,
    PaymentStatus paymentStatus,
    DeliveryStatus deliveryStatus,
    int totalPrice
) {

    public OrderProgressDto(Order entity) {
        this(entity.getId(),
            entity.getOrderItemList().stream().map(OrderItemCompleteDto::new).toList(),
            entity.getPaymentStatus(), entity.getDeliveryStatus(), entity.getTotalPrice());
    }

}
