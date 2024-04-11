package spharos.msg.domain.orders.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.orders.dto.OrderResponse.OrderProductDto;
import spharos.msg.domain.orders.dto.QOrderResponse_OrderProductDto;
import spharos.msg.domain.orders.entity.OrderProduct;
import spharos.msg.domain.orders.entity.QOrderProduct;
import spharos.msg.domain.orders.entity.QOrders;

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
        QOrders orders = QOrders.orders;
        return jpaQueryFactory
            .select(toOrderProductDto(orderProduct))
            .from(orderProduct)
            .innerJoin(orderProduct.orders, orders)
            .where(orders.id.eq(orderId))
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

    // 최근 한 달간 생성된 OrderProduct의 productId 필드를 조회

    public List<Long> findProductIdsCreatedLastMonth() {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        LocalDateTime startDate = LocalDateTime.now().minusMonths(1);

        return jpaQueryFactory
            .select(orderProduct.productId)
            .from(orderProduct)
            .where(orderProduct.createdAt.goe(startDate))
            .fetch();
    }

    public List<OrderProduct> findAllByOrderId(Long orderId) {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QOrders orders = QOrders.orders;

        return jpaQueryFactory
            .selectFrom(orderProduct)
            .innerJoin(orderProduct.orders, orders)
            .where(orders.id.eq(orderId))
            .fetch();
    }

    public OrderProduct findById(Long id) {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        return jpaQueryFactory
            .select(orderProduct)
            .from(orderProduct)
            .where(orderProduct.id.eq(id))
            .fetchOne();
    }

    private QOrderResponse_OrderProductDto toOrderProductDto(QOrderProduct orderProduct) {
        return new QOrderResponse_OrderProductDto(
            orderProduct.productId,
            orderProduct.productName,
            orderProduct.productPrice,
            orderProduct.productImage.coalesce(NO_IMAGE),
            orderProduct.orderQuantity,
            orderProduct.discountRate,
            orderProduct.productOption);
    }

}
