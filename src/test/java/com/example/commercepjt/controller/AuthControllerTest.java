package com.example.commercepjt.controller;

import com.example.commercepjt.common.enums.RoleStatus;

import com.example.commercepjt.domain.Role;
import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.domain.UserSeller;
import com.example.commercepjt.dto.request.UserRegisterDto;
import com.example.commercepjt.dto.response.UserRegisteredDto;
import com.example.commercepjt.repository.RoleRepository;
import com.example.commercepjt.repository.UserBuyerRepository;
import com.example.commercepjt.repository.UserSellerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserSellerRepository userSellerRepository;

    @Autowired
    private UserBuyerRepository userBuyerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("[Controller-Auth] 판매자 회원가입 성공")
    @Test
    void givenValidSeller_whenRegisterSeller_thenReturnUserRegisteredDto() throws Exception {
        // given
        UserRegisterDto requestDto = new UserRegisterDto("seller_jake01", "password123", RoleStatus.SELLER);

        // when & then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(
                new UserRegisteredDto(userSellerRepository.findByUsername("seller_jake01")))));
    }

    @DisplayName("[Controller-Auth] 판매자 회원가입 실패 - 이미 존재하는 사용자")
    @Test
    void givenExistingSeller_whenRegisterSeller_thenReturnBadRequest() throws Exception {
        // given
        Role sellerRole = roleRepository.save(new Role(RoleStatus.SELLER));
        UserSeller existingSeller = UserSeller.builder()
            .loginId("seller_jake02")
            .nickName("seller_jake02")
            .loginPassword("password123")
            .role(sellerRole)
            .build();
        userSellerRepository.save(existingSeller);

        UserRegisterDto requestDto = new UserRegisterDto("seller_jake02", "password123", RoleStatus.SELLER);

        // when & then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("[Controller-Auth] 구매자 회원가입 성공")
    @Test
    void givenValidBuyer_whenRegisterBuyer_thenReturnUserRegisteredDto() throws Exception {
        // given
        UserRegisterDto requestDto = new UserRegisterDto("buyer_jake01", "password123", RoleStatus.BUYER);

        // when & then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(
                new UserRegisteredDto(userBuyerRepository.findByUsername("buyer_jake01")))));
    }

    @DisplayName("[Controller-Auth] 구매자 회원가입 실패 - 이미 존재하는 사용자")
    @Test
    void givenExistingBuyer_whenRegisterBuyer_thenReturnBadRequest() throws Exception {
        // given
        Role buyerRole = roleRepository.save(new Role(RoleStatus.BUYER));
        UserBuyer existingBuyer = UserBuyer.builder()
            .loginId("buyer_jake02")
            .nickName("buyer_jake02")
            .loginPassword("password123")
            .role(buyerRole)
            .build();
        userBuyerRepository.save(existingBuyer);

        UserRegisterDto requestDto = new UserRegisterDto("buyer_jake02", "password123", RoleStatus.BUYER);

        // when & then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isBadRequest());
    }
}