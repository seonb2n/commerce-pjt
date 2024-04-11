package com.example.commercepjt.service;

import org.springframework.stereotype.Service;

@Service
public class AdminFacadeService {

    /**
     * 상품 별 마진율을 설정한다.
     * @param itemId
     * @param marginRate
     */
    void setProductMarginRate(long itemId, String marginRate) {}

    /**
     * 상품을 판매 중단할 수 있다.
     * @param itemId
     */
    void setProductStopSelling(long itemId) {}

    /**
     * 상품 판매를 시작한다.
     * @param itemId
     */
    void setProductStartSelling(long itemId) {}
}
