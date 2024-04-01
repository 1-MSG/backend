package spharos.msg.domain.product.repository;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductImage;
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    Optional<ProductImage> findByProductAndImageIndex(Product product, Integer index);

    List<ProductImage> findByProduct(Product product);
}
