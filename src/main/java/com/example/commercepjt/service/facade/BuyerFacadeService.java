package com.example.commercepjt.service.facade;

import com.example.commercepjt.dto.response.OrderCreatedDto;
import com.example.commercepjt.dto.response.OrderItemCreatedDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BuyerFacadeService {

    /**
     * 상품을 장바구니에 담는다.
     *
     * @param itemId
     * @param itemOrderCount
     * @return
     */
    OrderItemCreatedDto createItemOrderForBag(Long itemId, int itemOrderCount, long buyerId) {

    }

    /**
     * 장바구니에 담긴 상품을 확인한다.
     *
     * @param buyerId
     * @return
     */
    List<OrderItemCreatedDto> getOrderItemListInBag(long buyerId) {

    }

    /**
     * 장바구니에 담긴 상품을 주문한다.
     *
     * @param orderItemDtoList
     * @param buyerId
     * @return
     */
    OrderCreatedDto createOrder(List<OrderItemCreatedDto> orderItemDtoList, long buyerId) {

    }

    /**
     * 추천 상품 목록을 페이지로 조회할 수 있다.
     */
    void getRecommandedProductList() {}

    /**
     * 상품을 카테고리로 조회할 수 있다.
     * @param categoryId
     */
    void getProductListByCategory(long categoryId) {}

    /**
     * 상품을 키워드로 검색할 수 있다.
     * @param keyword
     */
    void getProductListByKeywork(String keyword) {}

    /**
     * 상품 구매 후기를 작성할 수 있다.
     * @param productId
     * @param orderId
     * @param content
     */
    void createProductReview(long productId, long orderId, String content) {}

    /**
     * 상품 배송 현황을 확인할 수 있다.
     * @param orderId
     */
    void checkProductOrderStatus(long orderId) {}

    /**
     * 배송 시작 전 단계의 상품을 환불할 수 있다.
     * @param orderId
     */
    void refundProductBeforeDelivery(long orderId) {}
}
