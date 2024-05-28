package com.example.commercepjt.service.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.commercepjt.common.enums.DeliveryStatus;
import com.example.commercepjt.domain.Category;
import com.example.commercepjt.domain.Item;
import com.example.commercepjt.domain.ItemMargin;
import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.domain.UserSeller;
import com.example.commercepjt.dto.response.ItemDto;
import com.example.commercepjt.dto.response.OrderCreatedDto;
import com.example.commercepjt.dto.response.OrderItemCreatedDto;
import com.example.commercepjt.repository.CategoryRepository;
import com.example.commercepjt.repository.ItemMarginRepository;
import com.example.commercepjt.repository.ItemRepository;
import com.example.commercepjt.repository.UserBuyerRepository;
import com.example.commercepjt.repository.UserSellerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SellerFacadeServiceTest {

    @Autowired
    private SellerFacadeService sellerFacadeService;

    @Autowired
    private BuyerFacadeService buyerFacadeService;

    private static UserSeller userSeller;
    private static Category category;

    private static Item item;

    private static UserBuyer userBuyer;
    @Autowired
    private ItemRepository itemRepository;

    @BeforeAll
    static void setUp(@Autowired UserSellerRepository userSellerRepository,
        @Autowired CategoryRepository categoryRepository,
        @Autowired ItemMarginRepository itemMarginRepository,
        @Autowired ItemRepository itemRepository,
        @Autowired UserBuyerRepository userBuyerRepository
    ) {
        userSeller = userSellerRepository.save(
            UserSeller.builder().nickName("seller").profit(0).build());
        category = categoryRepository.save(Category.builder().name("category").build());
        ItemMargin itemMargin = itemMarginRepository.save(
            ItemMargin.builder().marginRate("1.1").build());
        item = itemRepository.save(
            Item.builder().name("item").category(category).userSeller(userSeller).price(1000)
                .isSelling(true)
                .itemMargin(itemMargin).stockQuantity(1000).build());
        userBuyer = userBuyerRepository.save(
            UserBuyer.builder().loginId("buyer-id").loginPassword("buyer-pwd").point(1000).build());
    }

    @DisplayName("상품 업로드가 성공하면, id 가 포함된 상품 dto 가 반환된다.")
    @Test
    public void whenUploadItem_thenReturnItemDto() throws Exception {
        //when
        var responseDto = createItem("name", "description", 1000, 10);

        //then
        assertEquals(userSeller.getNickName(), responseDto.userSellerName());
    }

    @DisplayName("주문이 생성되면, 판매자는 배송 준비로 상태를 변경할 수 있다.")
    @Test
    public void whenChangeDeliveryStatusToReady_thenReturnOrderStatus() throws Exception {
        //given
        int originStockQuantity = itemRepository.findById(1L).get().getStockQuantity();
        OrderCreatedDto orderCreatedDto = createOrder();

        //when
        sellerFacadeService.changeProductDeliveryStatusToReady(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());

        //then
        var nowOrder = buyerFacadeService.checkProductOrderStatus(orderCreatedDto.id());
        assertEquals(DeliveryStatus.READY, nowOrder.deliveryStatus());
        assertEquals(originStockQuantity - 10,
            itemRepository.findById(1L).get().getStockQuantity());
    }

    @DisplayName("준비중인 주문을, 판매자는 배송 중 상태로 변경할 수 있다.")
    @Test
    public void whenChangeDeliveryStatusToTransit_thenReturnOrderStatus() throws Exception {
        //given
        OrderCreatedDto orderCreatedDto = createOrder();
        sellerFacadeService.changeProductDeliveryStatusToReady(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());

        //when
        sellerFacadeService.changeProductDeliveryStatusToTransit(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());

        //then
        var nowOrder = buyerFacadeService.checkProductOrderStatus(orderCreatedDto.id());
        assertEquals(DeliveryStatus.IN_TRANSIT, nowOrder.deliveryStatus());
    }

    @DisplayName("준비중인 주문을 판매자는 취소 상태로 변경할 수 있다")
    @Test
    public void whenChangeDeliveryStatusToCancelFromReady_thenReturnOrderStatus() throws Exception {
        //given
        OrderCreatedDto orderCreatedDto = createOrder();
        int originStockQuantity = itemRepository.findById(1L).get().getStockQuantity();
        sellerFacadeService.changeProductDeliveryStatusToReady(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());

        //when
        sellerFacadeService.changeProductDeliveryStatusToCancel(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());

        //then
        var nowOrder = buyerFacadeService.checkProductOrderStatus(orderCreatedDto.id());
        assertEquals(DeliveryStatus.CANCEL, nowOrder.deliveryStatus());
        assertEquals(originStockQuantity + 10,
            itemRepository.findById(1L).get().getStockQuantity());
    }

    @DisplayName("생성된 주문을, 판매자는 취소 상태로 변경할 수 있다.")
    @Test
    public void whenChangeDeliveryStatusToCancelFromCreated_thenReturnOrderStatus()
        throws Exception {
        //given
        OrderCreatedDto orderCreatedDto = createOrder();

        //when
        sellerFacadeService.changeProductDeliveryStatusToCancel(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());

        //then
        var nowOrder = buyerFacadeService.checkProductOrderStatus(orderCreatedDto.id());
        assertEquals(DeliveryStatus.CANCEL, nowOrder.deliveryStatus());
    }

    @DisplayName("배송중인 경우에는 주문 상태를 취소로 변경할 수 없다.")
    @Test
    public void whenOrderIsShipping_thenCannotChangeOrderStatus() throws Exception {
        //given
        OrderCreatedDto orderCreatedDto = createOrder();
        sellerFacadeService.changeProductDeliveryStatusToReady(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());
        sellerFacadeService.changeProductDeliveryStatusToTransit(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());

        //when & then
        assertThrows(RuntimeException.class,
            () -> sellerFacadeService.changeProductDeliveryStatusToCancel(userSeller.getId(),
                orderCreatedDto.orderItemCompleteDtoList().get(0).id()));
    }

    @DisplayName("상품 판매를 중단하면, 해당 상품의 상태는 판매 중지가 된다.")
    @Test
    public void whenStopSelling_thenChangeItemStatus() throws Exception {
        //when
        sellerFacadeService.setProductStopSelling(userSeller.getId(), item.getId());

        //then
        assertFalse(itemRepository.findById(item.getId()).get().isSelling());
    }

    @DisplayName("상품 판매를 재개하면, 해당 상품의 상태는 판매가 된다.")
    @Test
    public void whenResumeSelling_thenChangeItemStatus() throws Exception {
        //when
        sellerFacadeService.setProductSelling(userSeller.getId(), item.getId());

        //then
        assertTrue(itemRepository.findById(item.getId()).get().isSelling());
    }

    @DisplayName("자신이 판매 중인 상품 목록을 조회할 수 있다.")
    @Test
    public void whenFindMyItems_thenReturnItemsList() throws Exception {
        //when
        var sellingItemList = sellerFacadeService.getMySellingProductList(userSeller.getId());

        //then
        assertEquals(1, sellingItemList.size());
    }

    @DisplayName("n 건의 주문이 발생하고, 배송이 완료되면 판매자의 총 수익은 n * 상품 가격이다.")
    @Test
    public void whenOrdersAndCompleteShipping_thenGotProfits() throws Exception {
        //given
        OrderCreatedDto orderCreatedDto = createOrder();
        sellerFacadeService.changeProductDeliveryStatusToReady(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());
        sellerFacadeService.changeProductDeliveryStatusToTransit(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());
        sellerFacadeService.changeProductDeliveryStatusToDone(userSeller.getId(),
            orderCreatedDto.orderItemCompleteDtoList().get(0).id());

        //when
        var income = sellerFacadeService.getMySellingProductTotalIncome(userSeller.getId());

        //then
        assertEquals(11000, income);
    }

    private ItemDto createItem(String name, String description, int price, int stockQuantity) {
        return sellerFacadeService.uploadProduct(userSeller.getId(), category.getId(), name,
            description, price,
            stockQuantity);
    }

    private OrderCreatedDto createOrder() throws Exception {
        buyerFacadeService.createItemOrderForBag(item.getId(), 10, userBuyer.getId());
        return buyerFacadeService.createOrder(userBuyer.getId());
    }
}