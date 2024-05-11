package com.example.commercepjt.domain;

import com.example.commercepjt.common.enums.DeliveryStatus;
import com.example.commercepjt.common.enums.PaymentStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_BUYER_ID")
    private UserBuyer userBuyer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private int totalPrice;

    @Builder
    public Order(UserBuyer userBuyer, List<OrderItem> orderItemList, PaymentStatus paymentStatus) {
        this.userBuyer = userBuyer;
        this.orderItemList = orderItemList;
        this.paymentStatus = paymentStatus;
        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getItemQuantity() * orderItem.getPurchasedItemPrice();
        }
        this.totalPrice = totalPrice;
    }

    public DeliveryStatus getDeliveryStatus() {
        if (orderItemList.isEmpty()) {
            return DeliveryStatus.CREATED;
        }
        DeliveryStatus minStatus = orderItemList.get(0).getDeliveryStatus();
        for (OrderItem item : orderItemList) {
            if (item.getDeliveryStatus().compareTo(minStatus) < 0) {
                minStatus = item.getDeliveryStatus();
            }
        }
        return minStatus;
    }
}
