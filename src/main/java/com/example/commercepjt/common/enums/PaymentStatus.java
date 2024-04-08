package com.example.commercepjt.common.enums;

public enum PaymentStatus {

    READY("결제 대기"), IN_PROGRESS("결재 중"), DONE("결재 완료"), REFUND_PROGRESS("환불 진행 중"), REFUND_DONE("환불 완료");

    private String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}
