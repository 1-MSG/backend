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

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //카테고리 별 상품 반환
//    @Query("SELECT cp.product FROM CategoryProduct cp WHERE cp.category.categoryName = :categoryName")
    @Query("SELECT cp.product FROM CategoryProduct cp WHERE cp.category.id IN (SELECT c.id FROM Category c WHERE c.categoryName = :categoryName OR c.parent.categoryName = :categoryName)")
    List<Product> findProductsByCategoryName(@Param("categoryName") String categoryName);

    //랜덤 상품 반환
    @Query("SELECT p FROM Product p ORDER BY RAND()")
    List<Product> findRandomProducts();

    //패션 상품 반환
    @Query("SELECT cp.product FROM CategoryProduct cp WHERE cp.category.id IN (SELECT c.id FROM Category c WHERE c.categoryName = '패션잡화' OR c.parent.categoryName = '패션잡화')")
    Page<Product> findFashionProducts(Pageable pageable);

    @EntityGraph(attributePaths = {"productSalesInfo"})
    Optional<Product> findById(Long productId);

    Page<Product> findAllByOrderByProductSalesInfoProductSellTotalCountDesc(Pageable pageable);
}