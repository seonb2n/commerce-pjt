package com.example.commercepjt.service.facade;

import com.example.commercepjt.domain.Category;
import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.domain.UserSeller;
import com.example.commercepjt.repository.CategoryRepository;
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

    private static UserBuyer userBuyer;

    @BeforeAll
    static void setUp(@Autowired UserSellerRepository userSellerRepository,
        @Autowired CategoryRepository categoryRepository) {
        userSeller = userSellerRepository.save(
            UserSeller.builder().nickName("seller").profit(0).build());
        category = categoryRepository.save(Category.builder().name("category").build());
    }

    @DisplayName("상품을 장바구니에 추가할 수 있다.")
    @Test
    public void whenAddItemToBag_thenReturnOrderItem() throws Exception {
        //given

        //when

        //then
    }

    @DisplayName("상품을 장바구니에 담으면, 장바구니에서 상품 목록을 확인할 수 있다.")
    @Test
    public void whenAddItemToBag_thenReturnOrderItemDtoList() throws Exception {
        //when

        //then
    }

    @DisplayName("장바구니에 담긴 상품들로 주문을 할 수 있다.")
    @Test
    public void whenUsingOrderItemsInBag_thenCreateOrder() throws Exception {
        //given

        //when

        //then
    }
}