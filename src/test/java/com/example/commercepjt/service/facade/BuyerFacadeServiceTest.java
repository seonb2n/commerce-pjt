package com.example.commercepjt.service.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.commercepjt.domain.Category;
import com.example.commercepjt.domain.Item;
import com.example.commercepjt.domain.ItemMargin;
import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.domain.UserSeller;
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
class BuyerFacadeServiceTest {

    @Autowired
    private BuyerFacadeService service;

    private static UserSeller userSeller;
    private static Category category;
    private static Item item;

    private static UserBuyer userBuyer;

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
            Item.builder().name("item").category(category).userSeller(userSeller)
                .itemMargin(itemMargin).stockQuantity(100).build());
        userBuyer = userBuyerRepository.save(
            UserBuyer.builder().loginId("buyer-id").loginPassword("buyer-pwd").point(1000).build());
    }

    @DisplayName("상품을 장바구니에 추가할 수 있다.")
    @Test
    public void whenAddItemToBag_thenReturnOrderItem() throws Exception {
        //when
        var response = service.createItemOrderForBag(item.getId(), 10, userBuyer.getId());

        //then
        assertNotNull(response.id());
    }

    @DisplayName("상품을 장바구니에 담으면, 장바구니에서 상품 목록을 확인할 수 있다.")
    @Test
    public void whenAddItemToBag_thenReturnOrderItemDtoList() throws Exception {
        //when
        var response = service.createItemOrderForBag(item.getId(), 10, userBuyer.getId());
        var myBag = service.getOrderItemListInBag(userBuyer.getId());

        //then
        assertNotNull(response.id());
        assertEquals(1, myBag.size());
    }

    @DisplayName("장바구니에 담긴 상품들을 제거할 수 있다.")
    @Test
    public void givenItemsInBag_whenClearItemsInBag() throws Exception {
        //given
        service.createItemOrderForBag(item.getId(), 10, userBuyer.getId());


        //when
        service.clearItemListInBag(userBuyer.getId());
        var myBag = service.getOrderItemListInBag(userBuyer.getId());

        //then
        assertEquals(0, myBag.size());
    }

    @DisplayName("장바구니에 담긴 상품들로 주문을 할 수 있다.")
    @Test
    public void whenUsingOrderItemsInBag_thenCreateOrder() throws Exception {
        //given
        service.clearItemListInBag(userBuyer.getId());

        //when
        var response = service.createItemOrderForBag(item.getId(), 10, userBuyer.getId());
        var myBag = service.getOrderItemListInBag(userBuyer.getId());
        var myOrder = service.createOrder(userBuyer.getId());

        //then
        assertNotNull(response.id());
        assertEquals(1, myBag.size());
        assertNotNull(myOrder.id());
    }
}