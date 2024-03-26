package spharos.msg.domain.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.coupon.entity.Coupon;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class UserCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id")
    private Long id;

    @Column(columnDefinition = "boolean default false")
    @NotNull
    private Boolean isCouponUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Builder
    public UserCoupon(Boolean isCouponUsed, Users users, Coupon coupon) {
        this.isCouponUsed = isCouponUsed;
        this.users = users;
        this.coupon = coupon;
    }
}
