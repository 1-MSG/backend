package spharos.msg.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.orders.entity.OrderProduct;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByProduct(Product product, Pageable pageable);

    Long countByUserIdAndOrderProduct(Long userId, OrderProduct orderProduct);
}
