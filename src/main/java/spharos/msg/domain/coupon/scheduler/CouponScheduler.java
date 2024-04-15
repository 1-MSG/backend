package spharos.msg.domain.coupon.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spharos.msg.domain.coupon.service.CouponServiceV1;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class CouponScheduler {
    private final CouponServiceV1 couponService;
    @Scheduled(cron = "0 0 0 * * *")    //매일 00시
    public void deleteExpiredCoupon(){
        couponService.deleteExpiredCoupon();
    }
}