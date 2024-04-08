package com.example.commercepjt.common.enums;

public enum DeliveryStatus {

    READY("준비 중"), IN_TRANSIT("배송 중"), DONE("배송 완료");

    private String value;

    DeliveryStatus(String value) {
        this.value = value;
    }
}
