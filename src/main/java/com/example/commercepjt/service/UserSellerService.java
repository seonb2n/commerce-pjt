package com.example.commercepjt.service;

import com.example.commercepjt.domain.UserSeller;
import com.example.commercepjt.repository.UserSellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSellerService {

    private final UserSellerRepository userSellerRepository;

    public UserSeller getUserSellerById(Long id) {
        return userSellerRepository.findById(id).orElseThrow();
    }

}
