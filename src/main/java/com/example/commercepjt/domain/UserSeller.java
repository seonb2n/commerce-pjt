package com.example.commercepjt.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_SELLER")
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "USER_SELLER_ID"))
public class UserSeller extends User {

    @OneToMany(mappedBy = "userSeller", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    private int profit;
}
