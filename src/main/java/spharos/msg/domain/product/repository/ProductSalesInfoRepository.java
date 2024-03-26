package spharos.msg.domain.product.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.product.entity.ProductSalesInfo;

@Repository
public interface ProductSalesInfoRepository extends JpaRepository<ProductSalesInfo, Long> {

}
