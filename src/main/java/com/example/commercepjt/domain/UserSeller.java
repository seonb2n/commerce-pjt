package com.example.commercepjt.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "USER_SELLER")
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "USER_SELLER_ID"))
@NoArgsConstructor
public class UserSeller extends User {

    @OneToMany(mappedBy = "userSeller", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    private int profit;

    @Builder
    public UserSeller(Long id, String loginId, String loginPassword, String nickName,
        List<Item> items,
        int profit, Role role) {
        super(id, loginId, loginPassword, nickName, role);
        this.items = items;
        this.profit = profit;
    }
}
