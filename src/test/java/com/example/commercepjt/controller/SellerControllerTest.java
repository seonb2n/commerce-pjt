package com.example.commercepjt.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.commercepjt.common.enums.DeliveryStatus;
import com.example.commercepjt.common.utils.auth.JwtUtil;
import com.example.commercepjt.dto.request.ItemCreateDto;
import com.example.commercepjt.dto.request.ItemDeliveryStatusChangeDto;
import com.example.commercepjt.dto.response.ItemDto;
import com.example.commercepjt.dto.response.OrderItemProgressDto;
import com.example.commercepjt.service.facade.SellerFacadeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(SellerController.class)
@ActiveProfiles("test")
class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerFacadeService sellerFacadeService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("[Controller-Seller] 상품 업로드 호출")
    @Test
    @WithMockUser(username = "jake", roles = "SELLER")
    void givenUploadProduct_whenUploadProduct_thenReturnItemCreateDto() throws Exception {
        // given
        ItemCreateDto request = new ItemCreateDto(1L, 2L, "Product", "Description", 100, 10);
        ItemDto response = new ItemDto(1L, "seller", "Product", "Description", 100, true);

        when(sellerFacadeService.uploadProduct(request.sellerId(), request.categoryId(),
            request.name(), request.description(), request.price(),
            request.stockQuantity())).thenReturn(response);

        // when & then
        mockMvc.perform(
                post("/api/v1/seller/upload").with(SecurityMockMvcRequestPostProcessors.csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("[Controller-Seller] 내 판매 상품 조회")
    @Test
    @WithMockUser(username = "jake", roles = "SELLER")
    void givenProductList_whenGetSellingList_thenReturnItemDtoList() throws Exception {
        // given
        List<ItemDto> response = List.of(
            new ItemDto(1L, "seller", "Product", "Description", 100, true));

        when(sellerFacadeService.getMySellingProductList(1L)).thenReturn(response);

        // when & then
        mockMvc.perform(
                get("/api/v1/seller/selling-list/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("[Controller-Seller] 내 판매 총액 조회")
    @Test
    @WithMockUser(username = "jake", roles = "SELLER")
    void givenSellingProductIncome_whenGetTotalIncome_thenReturnIncome() throws Exception {
        // given
        int response = 1000;

        when(sellerFacadeService.getMySellingProductTotalIncome(1L)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/seller/selling-summary/1").with(
                SecurityMockMvcRequestPostProcessors.csrf())).andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(response)));
    }


    @DisplayName("[Controller-Seller] 주문 상품 상태 변경 ready")
    @Test
    @WithMockUser(username = "jake", roles = "SELLER")
    public void given_whenChangeProductStatusToReady_thenReturn() throws Exception {
        //given
        OrderItemProgressDto response = new OrderItemProgressDto(1L, 1L, 10, 1000,
            DeliveryStatus.READY);
        ItemDeliveryStatusChangeDto request = new ItemDeliveryStatusChangeDto(1L, 1L,
            DeliveryStatus.READY);

        when(sellerFacadeService.changeProductDeliveryStatusToReady(1L, 1L)).thenReturn(response);

        //when & then
        mockMvc.perform(
                patch("/api/v1/seller/change-delivery-status").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("[Controller-Seller] 주문 상품 상태 변경 transit")
    @Test
    @WithMockUser(username = "jake", roles = "SELLER")
    public void given_whenChangeProductStatusToTransit_thenReturn() throws Exception {
        //given
        OrderItemProgressDto response = new OrderItemProgressDto(1L, 1L, 10, 1000,
            DeliveryStatus.IN_TRANSIT);
        ItemDeliveryStatusChangeDto request = new ItemDeliveryStatusChangeDto(1L, 1L,
            DeliveryStatus.IN_TRANSIT);

        when(sellerFacadeService.changeProductDeliveryStatusToTransit(1L, 1L)).thenReturn(response);

        //when & then
        mockMvc.perform(
                patch("/api/v1/seller/change-delivery-status").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("[Controller-Seller] 주문 상품 상태 변경 done")
    @Test
    @WithMockUser(username = "jake", roles = "SELLER")
    public void given_whenChangeProductStatusToDone_thenReturn() throws Exception {
        //given
        OrderItemProgressDto response = new OrderItemProgressDto(1L, 1L, 10, 1000,
            DeliveryStatus.DONE);
        ItemDeliveryStatusChangeDto request = new ItemDeliveryStatusChangeDto(1L, 1L,
            DeliveryStatus.DONE);

        when(sellerFacadeService.changeProductDeliveryStatusToDone(1L, 1L)).thenReturn(response);

        //when & then
        mockMvc.perform(
                patch("/api/v1/seller/change-delivery-status").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("[Controller-Seller] 주문 상품 상태 변경 cancel")
    @Test
    @WithMockUser(username = "jake", roles = "SELLER")
    public void given_whenChangeProductStatusToCancel_thenReturn() throws Exception {
        //given
        OrderItemProgressDto response = new OrderItemProgressDto(1L, 1L, 10, 1000,
            DeliveryStatus.CANCEL);
        ItemDeliveryStatusChangeDto request = new ItemDeliveryStatusChangeDto(1L, 1L,
            DeliveryStatus.CANCEL);

        when(sellerFacadeService.changeProductDeliveryStatusToCancel(1L, 1L)).thenReturn(response);

        //when & then
        mockMvc.perform(
                patch("/api/v1/seller/change-delivery-status").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}