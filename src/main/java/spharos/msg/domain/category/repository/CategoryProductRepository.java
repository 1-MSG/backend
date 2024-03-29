package spharos.msg.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.category.entity.CategoryProduct;
import spharos.msg.domain.product.entity.Product;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {
    CategoryProduct findByProduct(Product product);
}
