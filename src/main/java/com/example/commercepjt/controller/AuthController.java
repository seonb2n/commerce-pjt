package com.example.commercepjt.controller;

import com.example.commercepjt.domain.User;
import com.example.commercepjt.dto.request.UserLoginDto;
import com.example.commercepjt.dto.request.UserRegisterDto;
import com.example.commercepjt.dto.response.UserLoginedDto;
import com.example.commercepjt.dto.response.UserRegisteredDto;
import com.example.commercepjt.service.auth.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisteredDto> registerUser(@RequestBody UserRegisterDto requestDto) {
        try {
            User user = userAuthService.registerNewUser(requestDto.username(), requestDto.password(), requestDto.roleStatus());
            return ResponseEntity.ok(new UserRegisteredDto(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginedDto> createAuthenticationToken(@RequestBody UserLoginDto authRequest) {
        try {
            String jwt = userAuthService.authenticate(authRequest.loginId(), authRequest.loginPassword());
            return ResponseEntity.ok(new UserLoginedDto(jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
