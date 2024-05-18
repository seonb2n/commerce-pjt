package com.example.commercepjt.common.enums;

public enum DeliveryStatus {

    IN_BAG("장바구니"), CREATED("주문 생성"), READY("준비 중"), IN_TRANSIT("배송 중"), DONE("배송 완료"), CANCEL("주문 취소");

    private String value;

    DeliveryStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
