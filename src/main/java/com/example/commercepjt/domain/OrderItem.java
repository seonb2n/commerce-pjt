package com.example.commercepjt.domain;

import com.example.commercepjt.common.enums.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ORDER_ITEM")
@Getter @Setter
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITME_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_BUYER_ID")
    private UserBuyer userBuyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private int itemQuantity;

    private int purchasedItemPrice;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Builder
    public OrderItem(UserBuyer userBuyer, Item item, Order order, int itemQuantity,
        int purchasedItemPrice) {
        this.userBuyer = userBuyer;
        this.item = item;
        this.order = order;
        this.itemQuantity = itemQuantity;
        this.purchasedItemPrice = purchasedItemPrice;
        this.deliveryStatus = DeliveryStatus.CREATED;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        switch (deliveryStatus) {
            case READY -> this.setDeliveryStatusToReady();
            case IN_TRANSIT -> this.setDeliveryStatusToInTransit();
            case DONE -> this.setDeliveryStatusToDone();
            case CANCEL -> this.setDeliveryStatusToCancel();
        }
    }

    private void setDeliveryStatusToReady() {
        if (this.deliveryStatus == DeliveryStatus.CREATED) {
            this.deliveryStatus = DeliveryStatus.READY;
            return;
        }
        throw new RuntimeException("Delivery status can be changed from created");
    }

    private void setDeliveryStatusToInTransit() {
        if (this.deliveryStatus == DeliveryStatus.READY) {
            this.deliveryStatus = DeliveryStatus.IN_TRANSIT;
            return;
        }
        throw new RuntimeException("Delivery status can be changed from ready");
    }

    private void setDeliveryStatusToDone() {
        if (this.deliveryStatus == DeliveryStatus.IN_TRANSIT) {
            this.deliveryStatus = DeliveryStatus.DONE;
            return;
        }
        throw new RuntimeException("Delivery status can be changed from in transit");
    }

    private void setDeliveryStatusToCancel() {
        if (this.deliveryStatus == DeliveryStatus.CREATED
            || this.deliveryStatus == DeliveryStatus.READY) {
            this.deliveryStatus = DeliveryStatus.CANCEL;
            this.getItem().addStockQuantity(this.itemQuantity); // 주문이 취소되면 상품 재고를 원복한다.
            return;
        }
        throw new RuntimeException("Delivery status can be changed from created or ready");
    }
}
