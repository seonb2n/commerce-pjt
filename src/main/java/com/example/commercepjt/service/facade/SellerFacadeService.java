package com.example.commercepjt.service.facade;

import com.example.commercepjt.domain.Category;
import com.example.commercepjt.domain.Item;
import com.example.commercepjt.domain.ItemMargin;
import com.example.commercepjt.domain.UserSeller;
import com.example.commercepjt.dto.response.ItemDto;
import com.example.commercepjt.service.CategoryService;
import com.example.commercepjt.service.ItemMarginService;
import com.example.commercepjt.service.ItemService;
import com.example.commercepjt.service.UserSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class SellerFacadeService {

    private final UserSellerService userSellerService;
    private final CategoryService categoryService;
    private final ItemService itemService;
    private final ItemMarginService itemMarginService;

    private static final String MARGIN_RATE = "0.1";

    /**
     * 상품을 판매자가 업로드할 수 있다.
     *
     * @param sellerId
     * @param categoryId
     * @param name
     * @param description
     * @param price
     * @param stockQuantity
     */
    @Transactional
    public ItemDto uploadProduct(Long sellerId, Long categoryId, String name, String description,
        int price, int stockQuantity) {
        UserSeller userSeller = userSellerService.getUserSellerById(sellerId);
        Category category = categoryService.getCategoryById(categoryId);

        ItemMargin itemMargin = ItemMargin.builder().marginRate(MARGIN_RATE).build();
        itemMarginService.save(itemMargin);

        Item item = Item.builder()
            .category(category)
            .userSeller(userSeller)
            .itemMargin(itemMargin)
            .name(name)
            .description(description)
            .price(price)
            .stockQuantity(stockQuantity)
            .isSelling(true).
            build();

        return new ItemDto(itemService.save(item), userSeller.getNickName());
    }

    /**
     * 상품 배송 상태를 생성에서 준비로 변경할 수 있다.
     *
     * @param orderId
     * @param deliveryStatus
     */
    void changeProductDeliveryStatusToReady(long sellerId, long orderId) {
    }

    /**
     * 상품 배송 상태를 준비에서 배송으로 변경할 수 있다.
     *
     * @param orderId
     * @param deliveryStatus
     */
    void changeProductDeliveryStatusToTransit(long sellerId, long orderId) {
    }

    /**
     * 상품 배송 상태를 주문 생성에서 주문 취소로 변경할 수 있다.
     *
     * @param orderId
     * @param deliveryStatus
     */
    void changeProductDeliveryStatusToCancel(long sellerId, long orderId) {
    }

    /**
     * 상품 판매를 중단할 수 있다.
     *
     * @param itemId
     */
    void setProductStopSelling(long sellerId, long itemId) {
    }

    /**
     * 상품 판매를 재개할 수 있다.
     *
     * @param itemId
     */
    void setProductSelling(long sellerId, long itemId) {
    }

    /**
     * 자신이 판매 중인 상품 목록을 확인할 수 있다.
     *
     * @param sellerId
     */
    void getMySellingProductList(long sellerId) {
    }

    /**
     * 나의 총 수익을 확인할 수 있다.
     *
     * @param sellerId
     */
    void getMySellingProductTotalIncome(long sellerId) {
    }
}
