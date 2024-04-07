package spharos.msg.domain.product.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.product.entity.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //랜덤 상품 반환
    @Query("SELECT p FROM Product p ORDER BY RAND()")
    List<Product> findTop12RandomProducts();

    Optional<Product> findById(Long productId);

    Page<Product> findAllByOrderByProductSalesInfoProductSellTotalCountDesc(Pageable pageable);
}