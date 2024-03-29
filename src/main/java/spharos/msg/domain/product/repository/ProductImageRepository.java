package spharos.msg.domain.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductImage;
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findByProduct(Product product);
}
