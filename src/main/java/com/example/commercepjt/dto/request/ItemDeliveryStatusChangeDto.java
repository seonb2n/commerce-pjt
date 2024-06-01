package com.example.commercepjt.dto.request;

import com.example.commercepjt.common.enums.DeliveryStatus;

public record ItemDeliveryStatusChangeDto(Long sellerId, Long orderItemId, DeliveryStatus deliveryStatus) {

}
