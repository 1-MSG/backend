package spharos.msg.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.users.entity.UserCoupon;
import spharos.msg.domain.users.entity.Users;

import java.util.List;

public interface UsersCouponRepository extends JpaRepository<UserCoupon, Long> {

    List<UserCoupon> findByUsers(Users users);
}
