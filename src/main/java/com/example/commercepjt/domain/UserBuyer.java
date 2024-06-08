package com.example.commercepjt.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER_BUYER")
@Getter @Setter
@AttributeOverride(name = "id", column = @Column(name = "USER_BUYER_ID"))
@NoArgsConstructor
public class UserBuyer extends User {

    @OneToMany(mappedBy = "userBuyer")
    private List<Order> orderList = new ArrayList<>();

    private int point;

    @Builder
    public UserBuyer(Long id, String loginId, String loginPassword, String nickName, int point, Role role) {
        super(id, loginId, loginPassword, nickName, role);
        this.point = point;
    }
}
