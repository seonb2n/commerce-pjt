package com.example.commercepjt.repository;

import com.example.commercepjt.domain.UserBuyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBuyerRepository extends JpaRepository<UserBuyer, Long> {

    UserBuyer findByUsername(String username);

}
