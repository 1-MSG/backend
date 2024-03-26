package spharos.msg.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.coupon.dto.CouponResponseDto;
import spharos.msg.domain.coupon.entity.Coupon;
import spharos.msg.domain.coupon.repository.CouponRepository;
import spharos.msg.domain.users.entity.UserCoupon;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersCouponRepository;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.code.status.SuccessStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final UsersRepository usersRepository;
    private final UsersCouponRepository usersCouponRepository;

    @Transactional
    public ApiResponse<?> getCoupon() {
        return ApiResponse.of(SuccessStatus.COUPON_LIST_GET_SUCCESS,
               couponRepository.findAll()
                       .stream()
                       .map(CouponResponseDto::new)
                       .collect(Collectors.toList()));
    }

    @Transactional
    public ApiResponse<?> downloadCoupon(String userUuid, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        Users users = usersRepository.findByUuid(userUuid).orElseThrow();

        for(UserCoupon userCoupon:usersCouponRepository.findByUsers(users)){
            if(userCoupon.getCoupon().getId().equals(couponId)){
                if(userCoupon.getIsCouponUsed()){
                    return ApiResponse.onFailure(ErrorStatus.ALREADY_USED_COUPON,null);
                }
                return ApiResponse.onFailure(ErrorStatus.ALREADY_HAD_COUPON,null);
            }
        }

        UserCoupon userCoupon = UserCoupon.builder()
                .isCouponUsed(false)
                .users(users)
                .coupon(coupon)
                .build();
        usersCouponRepository.save(userCoupon);

        return ApiResponse.of(SuccessStatus.COUPON_DOWNLOAD_SUCCESS,null);
    }

    @Transactional
    public ApiResponse<?> getUsersCoupon(String userUuid) {
        Users users = usersRepository.findByUuid(userUuid).orElseThrow();

        return ApiResponse.of(SuccessStatus.COUPON_GET_USERS_SUCCESS,
                usersCouponRepository.findByUsers(users)
                .stream()
                .map(CouponResponseDto::new)
                .collect(Collectors.toList()));
    }

    @Transactional
    public void deleteExpiredCoupon() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Coupon> couponToDelete = couponRepository.findByCouponExpirationBefore(currentDate);
        couponRepository.deleteAll(couponToDelete);
    }
}
