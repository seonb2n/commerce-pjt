package com.example.commercepjt.service.facade;

import com.example.commercepjt.common.enums.DeliveryStatus;
import com.example.commercepjt.common.enums.PaymentStatus;
import com.example.commercepjt.domain.Item;
import com.example.commercepjt.domain.Order;
import com.example.commercepjt.domain.OrderItem;
import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.dto.response.OrderCreatedDto;
import com.example.commercepjt.dto.response.OrderItemCreatedDto;
import com.example.commercepjt.dto.response.OrderProgressDto;
import com.example.commercepjt.service.ItemService;
import com.example.commercepjt.service.OrderItemService;
import com.example.commercepjt.service.OrderService;
import com.example.commercepjt.service.UserBuyerService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyerFacadeService {

    private final ItemService itemService;
    private final UserBuyerService userBuyerService;
    private final OrderItemService orderItemService;
    private final OrderService orderService;

    /**
     * 상품을 장바구니에 담는다.
     *
     * @param itemId
     * @param itemOrderCount
     * @return
     */
    @Transactional
    public OrderItemCreatedDto createItemOrderForBag(Long itemId, int itemOrderCount,
        long buyerId) {
        Item item = itemService.getItemById(itemId);
        UserBuyer buyer = userBuyerService.getUserBuyerById(buyerId);
        OrderItem orderItem = OrderItem.builder().userBuyer(buyer).item(item)
            .itemQuantity(itemOrderCount).build();

        OrderItem savedOrderItem = orderItemService.save(orderItem);

        return new OrderItemCreatedDto(savedOrderItem);
    }

    /**
     * 장바구니에 담긴 상품을 확인한다.
     *
     * @param buyerId
     * @return
     */
    public List<OrderItemCreatedDto> getOrderItemListInBag(long buyerId) {
        List<OrderItem> foundOrderItems = orderItemService.findAllItemsByBuyerIdAndStatusIsInBag(buyerId);

        return foundOrderItems.stream().map(OrderItemCreatedDto::new).collect(
            Collectors.toList());
    }

    /**
     * 장바구니에 담긴 상품을 주문한다.
     * todo 주문이 생성 된 후, 결제가 진행된다.
     * todo 장바구니에 담긴 주문 중 개별 결제가 가능하도록 변경 필요
     * @param buyerId
     * @return
     */
    @Transactional
    public OrderCreatedDto createOrder(long buyerId) throws Exception {
        UserBuyer buyer = userBuyerService.getUserBuyerById(buyerId);
        List<OrderItem> orderItemList = orderItemService.findAllItemsByBuyerIdAndStatusIsInBag(buyerId);

        for (OrderItem orderItem : orderItemList) {
            Item item = orderItem.getItem();
            orderItem.setPurchasedItemPrice(
                item.getPrice() * orderItem.getItemQuantity());
            item.minusStockQuantity(orderItem.getItemQuantity());
            orderItem.setDeliveryStatus(DeliveryStatus.CREATED);
        }

        Order order = Order.builder().userBuyer(buyer).orderItemList(orderItemList).paymentStatus(
            PaymentStatus.READY).build();

        Order savedOrder = orderService.save(order);
        return new OrderCreatedDto(savedOrder);
    }

    /**
     * 추천 상품 목록을 페이지로 조회할 수 있다.
     */
    void getRecommandedProductList() {
    }

    /**
     * 상품을 카테고리로 조회할 수 있다.
     *
     * @param categoryId
     */
    void getProductListByCategory(long categoryId) {
    }

    /**
     * 상품을 키워드로 검색할 수 있다.
     *
     * @param keyword
     */
    void getProductListByKeywork(String keyword) {
    }

    /**
     * 상품 구매 후기를 작성할 수 있다.
     *
     * @param productId
     * @param orderId
     * @param content
     */
    void createProductReview(long productId, long orderId, String content) {
    }

    /**
     * 상품 배송 현황을 확인할 수 있다.
     *
     * @param orderId
     */
    public OrderProgressDto checkProductOrderStatus(long orderId) {
        Order order = orderService.getOrderById(orderId);
        return new OrderProgressDto(order);
    }

    /**
     * 배송 시작 전 단계의 상품을 환불할 수 있다.
     *
     * @param orderId
     */
    void refundProductBeforeDelivery(long orderId) {
    }
}
