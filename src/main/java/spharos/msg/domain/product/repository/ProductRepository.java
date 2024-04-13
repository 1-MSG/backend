package spharos.msg.domain.product.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.repository.impl.ProductRepositoryCustomImpl;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
    ProductRepositoryCustom {

    //랜덤 상품 반환
    @Query(value = "SELECT * FROM product ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Product> findRandomProducts(@Param("limit") int limit);

    @EntityGraph(attributePaths = {"productSalesInfo"})
    Optional<Product> findById(Long productId);

    Page<Product> findAllByOrderByProductSalesInfoProductSellTotalCountDesc(Pageable pageable);

    List<Product> findTop11ByOrderByProductSalesInfoProductSellTotalCountDesc();

}