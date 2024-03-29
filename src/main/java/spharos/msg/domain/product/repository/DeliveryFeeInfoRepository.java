package spharos.msg.domain.product.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.product.entity.DeliveryFeeInfo;

public interface DeliveryFeeInfoRepository extends JpaRepository<DeliveryFeeInfo, Long> {

}
