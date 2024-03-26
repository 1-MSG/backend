package spharos.msg.domain.coupon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.coupon.service.CouponService;
import spharos.msg.global.api.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Coupon", description = "쿠폰 API")
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/coupon")
    private ApiResponse<?> getCoupon(
    ) {
        return couponService.getCoupon();
    }

    @PostMapping("/coupon/{couponId}")
    private ApiResponse<?> downloadCoupon(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long couponId
    ) {
        return couponService.downloadCoupon(userDetails.getUsername(), couponId);
    }

    @GetMapping("/coupon-user")
    private ApiResponse<?> getUsersCoupon(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return couponService.getUsersCoupon(userDetails.getUsername());
    }
}
