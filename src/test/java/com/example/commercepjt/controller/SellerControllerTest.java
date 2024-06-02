package com.example.commercepjt.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.commercepjt.dto.request.ItemCreateDto;
import com.example.commercepjt.dto.response.ItemDto;
import com.example.commercepjt.service.facade.SellerFacadeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(SellerController.class)
@ActiveProfiles("test")
class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerFacadeService sellerFacadeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUploadProduct() throws Exception {
        ItemCreateDto request = new ItemCreateDto(1L, 2L, "Product", "Description", 100, 10);
        ItemDto response = new ItemDto(1L, "seller", "Product", "Description", 100, true);

        when(
            sellerFacadeService.uploadProduct(request.sellerId(), request.categoryId(), request.name(), request.description(), request.price(), request.stockQuantity())).thenReturn(
            response);

        mockMvc.perform(post("/api/v1/seller/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }


    @Test
    void testGetMySellingList() throws Exception {
        List<ItemDto> response = List.of(new ItemDto(1L, "seller", "Product", "Description", 100, true));

        when(sellerFacadeService.getMySellingProductList(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/seller/selling-list/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testGetMySellingSummary() throws Exception {
        int response = 1000;

        when(sellerFacadeService.getMySellingProductTotalIncome(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/seller/selling-summary/1"))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(response)));
    }

    //todo 배송 상태 변경 테스트 코드 추가 필요
}