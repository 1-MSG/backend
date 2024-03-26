package spharos.msg.domain.coupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.coupon.entity.Coupon;
import spharos.msg.domain.users.entity.UserCoupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CouponResponseDto {
    private Long couponId;
    private BigDecimal couponDiscountRate;
    private String couponName;
    private LocalDateTime couponExpiration;

    public CouponResponseDto(Coupon coupon){
        this.couponId = coupon.getId();
        this.couponDiscountRate = coupon.getCouponDiscountRate();
        this.couponName = coupon.getCouponName();
        this.couponExpiration = coupon.getCouponExpiration();
    }
    public CouponResponseDto(UserCoupon userCoupon){
        this.couponId = userCoupon.getCoupon().getId();
        this.couponDiscountRate = userCoupon.getCoupon().getCouponDiscountRate();
        this.couponName = userCoupon.getCoupon().getCouponName();
        this.couponExpiration = userCoupon.getCoupon().getCouponExpiration();
    }
}