package spharos.msg.domain.orders.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.orders.dto.OrderResponse.OrderProductDto;
import spharos.msg.domain.orders.entity.OrderProduct;
import spharos.msg.domain.orders.entity.QOrderProduct;
import spharos.msg.domain.product.entity.QProduct;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderProductRepository {

    private static final String NO_IMAGE = "NONE";
    private static final int ORDER_PRODUCT_BATCH_SIZE = 10;
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public List<OrderProductDto> findAllById(Long orderId) {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QProduct product = QProduct.product;
        return jpaQueryFactory
            .select(Projections.constructor(OrderProductDto.class,
                orderProduct.productId,
                orderProduct.productName,
                orderProduct.productPrice,
                orderProduct.productImage.coalesce(NO_IMAGE),
                orderProduct.orderQuantity,
                orderProduct.discountRatio,
                orderProduct.productOption))
            .from(orderProduct, product)
            .where(orderProduct.id.eq(orderId))
            .fetch();
    }

    public void saveAll(List<OrderProduct> entities) {
        for (int i = 0; i < entities.size(); i++) {
            entityManager.persist(entities.get(i));
            if (i % ORDER_PRODUCT_BATCH_SIZE == 0 && i > 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }
}
