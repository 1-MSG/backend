package spharos.msg.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.coupon.entity.Coupon;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    List<Coupon> findByCouponExpirationBefore(LocalDateTime expireDate);
}
