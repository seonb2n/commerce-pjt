package com.example.commercepjt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String loginId;

    private String loginPassword;

    private String nickName;

}
