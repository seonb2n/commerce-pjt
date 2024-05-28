package com.example.commercepjt.repository;

import com.example.commercepjt.domain.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByUserSellerIdAndIsSellingTrue(Long userId);

    Optional<Item> findByIdAndUserSellerId(Long id, Long userId);

}
