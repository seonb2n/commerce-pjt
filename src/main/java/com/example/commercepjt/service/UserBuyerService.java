package com.example.commercepjt.service;

import com.example.commercepjt.domain.UserBuyer;
import com.example.commercepjt.repository.UserBuyerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBuyerService {

    private final UserBuyerRepository userBuyerRepository;

    public UserBuyer getUserBuyerById(long id) {
        return userBuyerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
