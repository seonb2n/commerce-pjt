package com.example.commercepjt.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_BUYER")
@Getter @Setter
@AttributeOverride(name = "id", column = @Column(name = "USER_BUYER_ID"))
public class UserBuyer extends User {

    @OneToMany(mappedBy = "userBuyer")
    private List<Order> orderList = new ArrayList<>();

    private int point;
}
