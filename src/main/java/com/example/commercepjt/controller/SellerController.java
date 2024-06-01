package com.example.commercepjt.controller;

import com.example.commercepjt.dto.request.ItemCreateDto;
import com.example.commercepjt.dto.request.ItemDeliveryStatusChangeDto;
import com.example.commercepjt.dto.request.ProductSellingStatusChangeDto;
import com.example.commercepjt.dto.response.ItemDto;
import com.example.commercepjt.dto.response.OrderItemProgressDto;
import com.example.commercepjt.service.facade.SellerFacadeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seller")
public class SellerController {

    private final SellerFacadeService sellerFacadeService;

    @PostMapping("/upload")
    ItemDto uploadProduct(@RequestBody ItemCreateDto request) {
        // todo body 에서 sellerId 를 추출하는 것이 아니라 사용자 인증 세션으로부터 사용자 id 를 추출해야 한다.
        return sellerFacadeService.uploadProduct(request.sellerId(), request.categoryId(),
            request.name(),
            request.description(), request.price(), request.stockQuantity());
    }

    @PatchMapping("/change-delivery-status")
    OrderItemProgressDto changeItemDeliveryStatus(
        @RequestBody ItemDeliveryStatusChangeDto request) {
        // todo body 에서 sellerId 를 추출하는 것이 아니라 사용자 인증 세션으로부터 사용자 id 를 추출해야 한다.
        switch (request.deliveryStatus()) {
            case IN_TRANSIT -> {
                return sellerFacadeService.changeProductDeliveryStatusToTransit(request.sellerId(),
                    request.orderItemId());
            }
            case DONE -> {
                return sellerFacadeService.changeProductDeliveryStatusToDone(request.sellerId(),
                    request.orderItemId());
            }
            case CANCEL -> {
                return sellerFacadeService.changeProductDeliveryStatusToCancel(request.sellerId(),
                    request.orderItemId());
            }
            default -> {
                return sellerFacadeService.changeProductDeliveryStatusToReady(request.sellerId(),
                    request.orderItemId());
            }
        }
    }

    @PatchMapping("/change-product-selling-stop")
        // todo body 에서 sellerId 를 추출하는 것이 아니라 사용자 인증 세션으로부터 사용자 id 를 추출해야 한다.
    ItemDto changeProductSellingStatusToStop(@RequestBody ProductSellingStatusChangeDto request) {
        return sellerFacadeService.setProductStopSelling(request.sellerId(), request.productId());
    }

    @PostMapping("/change-product-selling-resume")
    ItemDto changeProductSellingStatusToResume(@RequestBody ProductSellingStatusChangeDto request) {
        // todo body 에서 sellerId 를 추출하는 것이 아니라 사용자 인증 세션으로부터 사용자 id 를 추출해야 한다.
        return sellerFacadeService.setProductSelling(request.sellerId(), request.productId());
    }

    @GetMapping("/selling-list/{id}")
    List<ItemDto> getMySellingList(@PathVariable("id") Long sellerId) {
        return sellerFacadeService.getMySellingProductList(sellerId);
    }

    @GetMapping("/selling-summary/{id}")
    int getMySellingSummary(@PathVariable("id") Long sellerId) {
        return sellerFacadeService.getMySellingProductTotalIncome(sellerId);
    }
}
