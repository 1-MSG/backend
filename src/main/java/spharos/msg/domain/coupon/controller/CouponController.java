package spharos.msg.domain.coupon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.coupon.dto.CouponResponseDto;
import spharos.msg.domain.coupon.service.CouponServiceV2;
import spharos.msg.global.api.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "Coupon", description = "쿠폰 API")
public class CouponController {
    private final CouponServiceV2 couponService;

    @GetMapping("/coupon")
    private ApiResponse<List<CouponResponseDto>> getCoupon(
    ) {
        return couponService.getCoupon();
    }

    @PostMapping("/coupon/{couponId}")
    private ApiResponse<Void> downloadCoupon(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long couponId
    ) {
        return couponService.downloadCoupon(userDetails.getUsername(), couponId);
    }

    @GetMapping("/coupon-user")
    private ApiResponse<List<CouponResponseDto>> getUsersCoupon(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return couponService.getUsersCoupon(userDetails.getUsername());
    }
}
