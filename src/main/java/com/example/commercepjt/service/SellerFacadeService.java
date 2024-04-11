package com.example.commercepjt.service;

import com.example.commercepjt.common.enums.DeliveryStatus;
import org.springframework.stereotype.Service;

@Service
public class SellerFacadeService {

    /**
     * 상품을 판매자가 업로드할 수 있다.
     * @param sellerId
     * @param categoryId
     * @param name
     * @param description
     * @param price
     * @param stockQuantity
     */
    void uploadProduct(long sellerId, long categoryId, String name, String description, int price, int stockQuantity) {}

    /**
     * 상품 배송 상태를 변경할 수 있다.
     * @param orderId
     * @param deliveryStatus
     */
    void changeProductDeliveryStatus(long sellerId, long orderId, DeliveryStatus deliveryStatus) {}

    /**
     * 상품 판매를 중단할 수 있다.
     * @param itemId
     */
    void setProductStopSelling(long sellerId, long itemId) {}

    /**
     * 상품 판매를 재개할 수 있다.
     * @param itemId
     */
    void setProductSelling(long sellerId, long itemId) {}

    /**
     * 자신이 판매 중인 상품 목록을 확인할 수 있다.
     * @param sellerId
     */
    void getMySellingProductList(long sellerId) {}

    /**
     * 나의 총 수익을 확인할 수 있다.
     * @param sellerId
     */
    void getMySellingProductTotalIncome(long sellerId) {}
}
