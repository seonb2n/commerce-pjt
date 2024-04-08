package com.example.commercepjt.domain;

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
@Table(name = "USER_BUYER")
@Getter @Setter
public class UserBuyer extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "USER_BUYER_ID")
    private Long id;

    @OneToMany(mappedBy = "userBuyer")
    private List<Order> orderList = new ArrayList<>();

    private String loginId;

    private String loginPassword;

    private String nickName;

    private int point;
}
