package com.example.commercepjt.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SellerFacadeServiceTest {

    @Autowired
    private SellerFacadeService service;

    @DisplayName("상품 업로드가 성공하면, id 가 포함된 상품 dto 가 반환된다.")
    @Test
    public void whenUploadItem_thenReturnItemDto() throws Exception {
        //when


        //then
    }

    @DisplayName("배송 상태를 취소로 변경하면, 재고가 추가되고, 환불 절차에 들어간다.")
    @Test
    public void whenChangeOrderStatus_thenRollbackOrder() throws Exception {
        //given


        //when


        //then
    }

    @DisplayName("배송중인 경우에는 주문 상태를 변경할 수 없다.")
    @Test
    public void whenOrderIsShipping_thenCannotChangeOrderStatus() throws Exception {
        //given


        //when


        //then
    }

    @DisplayName("배송 완료된 경우에는 주문 상태를 변경할 수 없다.")
    @Test
    public void whenOrderIsComplete_thenCannotChangeOrderStatus() throws Exception {
        //given


        //when


        //then
    }

    @DisplayName("상품 판매를 중단하면, 해당 상품의 상태는 판매 중지가 된다.")
    @Test
    public void whenStopSelling_thenChangeItemStatus() throws Exception {
        //given


        //when


        //then
    }

    @DisplayName("상품 판매를 재개하면, 해당 상품의 상태는 판매가 된다.")
    @Test
    public void whenResumeSelling_thenChangeItemStatus() throws Exception {
        //given


        //when


        //then
    }

    @DisplayName("자신이 판매 중인 상품 목록을 조회할 수 있다.")
    @Test
    public void whenFindMyItems_thenReturnItemsList() throws Exception {
        //given


        //when


        //then
    }

    @DisplayName("n 건의 주문이 발생하고, 배송이 완료되면 판매자의 총 수익은 n * 상품 가격이다.")
    @Test
    public void whenOrdersAndCompleteShipping_thenGotProfits() throws Exception {
        //given


        //when


        //then
    }
}