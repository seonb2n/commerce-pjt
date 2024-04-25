package com.example.commercepjt.repository;

import com.example.commercepjt.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
