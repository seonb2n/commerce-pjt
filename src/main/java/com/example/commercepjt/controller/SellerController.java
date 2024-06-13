package com.example.commercepjt.controller;

import com.example.commercepjt.common.utils.auth.MyUserDetails;
import com.example.commercepjt.dto.request.ItemCreateDto;
import com.example.commercepjt.dto.request.ItemDeliveryStatusChangeDto;
import com.example.commercepjt.dto.request.ProductSellingStatusChangeDto;
import com.example.commercepjt.dto.response.ItemDto;
import com.example.commercepjt.dto.response.OrderItemProgressDto;
import com.example.commercepjt.service.facade.SellerFacadeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    ItemDto uploadProduct(@AuthenticationPrincipal MyUserDetails userDetails,
        @RequestBody ItemCreateDto request) {
        return sellerFacadeService.uploadProduct(userDetails.getUserId(), request.categoryId(),
            request.name(), request.description(), request.price(), request.stockQuantity());
    }

    @PatchMapping("/change-delivery-status")
    OrderItemProgressDto changeItemDeliveryStatus(
        @AuthenticationPrincipal MyUserDetails userDetails,
        @RequestBody ItemDeliveryStatusChangeDto request) {
        switch (request.deliveryStatus()) {
            case IN_TRANSIT -> {
                return sellerFacadeService.changeProductDeliveryStatusToTransit(
                    userDetails.getUserId(), request.orderItemId());
            }
            case DONE -> {
                return sellerFacadeService.changeProductDeliveryStatusToDone(
                    userDetails.getUserId(), request.orderItemId());
            }
            case CANCEL -> {
                return sellerFacadeService.changeProductDeliveryStatusToCancel(
                    userDetails.getUserId(), request.orderItemId());
            }
            default -> {
                return sellerFacadeService.changeProductDeliveryStatusToReady(
                    userDetails.getUserId(), request.orderItemId());
            }
        }
    }

    @PatchMapping("/change-product-selling-stop")
    ItemDto changeProductSellingStatusToStop(@AuthenticationPrincipal MyUserDetails userDetails,
        @RequestBody ProductSellingStatusChangeDto request) {
        return sellerFacadeService.setProductStopSelling(userDetails.getUserId(),
            request.productId());
    }

    @PostMapping("/change-product-selling-resume")
    ItemDto changeProductSellingStatusToResume(@AuthenticationPrincipal MyUserDetails userDetails,
        @RequestBody ProductSellingStatusChangeDto request) {
        return sellerFacadeService.setProductSelling(userDetails.getUserId(), request.productId());
    }

    @GetMapping("/selling-list")
    List<ItemDto> getMySellingList(@AuthenticationPrincipal MyUserDetails userDetails) {
        return sellerFacadeService.getMySellingProductList(userDetails.getUserId());
    }

    @GetMapping("/selling-summary")
    int getMySellingSummary(@AuthenticationPrincipal MyUserDetails userDetails) {
        return sellerFacadeService.getMySellingProductTotalIncome(userDetails.getUserId());
    }
}
