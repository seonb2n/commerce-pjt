package com.example.commercepjt.service;

import com.example.commercepjt.domain.Item;
import com.example.commercepjt.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item getItemById(long id) {
        return itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
