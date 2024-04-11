package spharos.msg.domain.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.msg.domain.category.entity.CategoryProduct;
import spharos.msg.domain.product.entity.Product;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long>,
    CategoryProductRepositoryCustom {

    CategoryProduct findByProduct(Product product);

    CategoryProduct findByProductId(Long productId);

    @Query(value = "SELECT cp FROM CategoryProduct cp WHERE cp.category.id = :categoryId ORDER BY RAND()")
    List<CategoryProduct> findRandomTop12ByCategoryId(@Param("categoryId") Long categoryId);
}
