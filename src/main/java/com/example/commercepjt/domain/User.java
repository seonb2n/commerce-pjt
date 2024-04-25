package com.example.commercepjt.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String loginId;

    private String loginPassword;

    private String nickName;

}
