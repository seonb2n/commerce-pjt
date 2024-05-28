package com.example.commercepjt.service;

import com.example.commercepjt.domain.Item;
import com.example.commercepjt.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
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

    public Item getItemByIdAndSellerId(long itemId, long sellerId) {
        return itemRepository.findByIdAndUserSellerId(itemId, sellerId)
            .orElseThrow(EntityNotFoundException::new);
    }

    public List<Item> findItemsBySellerIdAndIsSelling(long sellerId) {
        return itemRepository.findAllByUserSellerIdAndIsSellingTrue(sellerId);
    }

    public Item changeItemStatusToSelling(long sellerId, long itemId) {
        Item item = getItemByIdAndSellerId(itemId, sellerId);
        if (!item.isSelling()) {
            item.setSelling(true);
        }
        return item;
    }

    public Item changeItemStatusToNotSelling(long sellerId, long itemId) {
        Item item = getItemByIdAndSellerId(itemId, sellerId);
        if (item.isSelling()) {
            item.setSelling(false);
        }
        return item;
    }
}
