package spharos.msg.domain.coupon.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spharos.msg.domain.coupon.service.CouponService;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class CouponScheduler {
    private final CouponService couponService;
    @Scheduled(cron = "0 0 0 * * *")    //매일 00시
    public void deleteExpiredCoupon(){
        couponService.deleteExpiredCoupon();
    }
}