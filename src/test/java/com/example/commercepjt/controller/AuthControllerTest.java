package com.example.commercepjt.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.commercepjt.common.enums.RoleStatus;
import com.example.commercepjt.common.utils.auth.JwtUtil;
import com.example.commercepjt.domain.Role;
import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.domain.UserSeller;
import com.example.commercepjt.dto.request.UserLoginDto;
import com.example.commercepjt.dto.request.UserRegisterDto;
import com.example.commercepjt.dto.response.UserLoginedDto;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    @MockBean
    private JwtUtil jwtUtil;

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

    @DisplayName("[Controller-Auth] 판매자 회원가입 및 로그인 성공")
    @Test
    void givenValidUser_whenRegisterAndLoginUser_thenReturnJwtToken() throws Exception {
        // given
        String expectedJwt = "mocked-jwt-token";
        UserRegisterDto registerRequest = new UserRegisterDto("seller_jake03", "password123", RoleStatus.SELLER);
        UserLoginDto loginRequest = new UserLoginDto("seller_jake03", "password123");

        when(jwtUtil.generateToken("seller_jake03")).thenReturn(expectedJwt);

        // register user
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(
                new UserRegisteredDto(userSellerRepository.findByUsername("seller_jake03")))));

        // when & then: login user
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(new UserLoginedDto(expectedJwt))));
    }

    @DisplayName("[Controller-Auth] 판매자 회원가입 및 로그인 실패 - 비밀번호 불일치")
    @Test
    void givenValidUser_whenRegisterAndLoginWithInvalidPassword_thenReturnUnauthorized() throws Exception {
        // given
        UserRegisterDto registerRequest = new UserRegisterDto("seller_jake04", "password123", RoleStatus.SELLER);
        UserLoginDto loginRequest = new UserLoginDto("seller_jake04", "wrongpassword");

        // register user
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(
                new UserRegisteredDto(userSellerRepository.findByUsername("seller_jake04")))));

        // when & then: login user with wrong password
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isUnauthorized());
    }
}