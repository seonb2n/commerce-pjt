package com.example.commercepjt.common.enums;

public enum RoleStatus {

    BUYER("BUYER"), SELLER("SELLER"), ADMIN("ADMIN");

    private String value;

    RoleStatus(String value) {
        this.value = value;
    }
}
