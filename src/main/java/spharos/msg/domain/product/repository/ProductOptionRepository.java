package spharos.msg.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductOption;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {
    List<ProductOption> findByProduct(Product product);
}
