package com.example.commercepjt.service;

import com.example.commercepjt.domain.ItemMargin;
import com.example.commercepjt.repository.ItemMarginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemMarginService {

    private final ItemMarginRepository itemMarginRepository;

    public ItemMargin save(ItemMargin itemMargin) {
        return itemMarginRepository.save(itemMargin);
    }
}
