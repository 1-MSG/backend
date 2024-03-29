package spharos.msg.domain.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductDetailImage;
@Repository
public interface ProductDetailImageRepository extends JpaRepository<ProductDetailImage, Long> {
    List<ProductDetailImage> findByProduct(Product product);
}
