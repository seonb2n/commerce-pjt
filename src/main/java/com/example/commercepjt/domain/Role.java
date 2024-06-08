package com.example.commercepjt.domain;

import com.example.commercepjt.common.enums.RoleStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROLE")
@Getter @Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "ROLE_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus;

    public Role(RoleStatus roleStatus) {
        this.roleStatus = roleStatus;
    }
}
