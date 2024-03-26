package spharos.msg.domain.coupon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import spharos.msg.domain.users.entity.UserCoupon;
import spharos.msg.global.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_coupon_id")
    private Long id;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Column(columnDefinition = "decimal default 0.0")
    private BigDecimal couponDiscountRate;

    @NotBlank
    private String couponName;

    @NotNull(message = "쿠폰 만료 기간은 필수입니다.")
    private LocalDateTime couponExpiration;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCoupon> userCoupons = new ArrayList<>();
}
