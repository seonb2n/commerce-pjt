package com.example.commercepjt.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class UserSeller extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "USER_SELLER_ID")
    private Long id;

    @OneToMany(mappedBy = "userSeller", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    private String loginId;

    private String loginPassword;

    private String nickName;

    private int profit;
}
