package spharos.msg.domain.orders.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spharos.msg.domain.orders.dto.OrderResponse.OrderHistoryDto;
import spharos.msg.domain.orders.entity.QOrders;
import spharos.msg.domain.orders.repository.OrderRepositoryCustom;

@Component
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderHistoryDto> findAllByBuyerId(Long buyerId) {
        QOrders orders = QOrders.orders;
        return jpaQueryFactory
            .select(Projections.constructor(OrderHistoryDto.class,
                orders.id,
                orders.totalPrice,
                orders.createdAt))
            .from(orders)
            .where(orders.userId.eq(buyerId))
            .orderBy(orders.createdAt.desc())
            .distinct()
            .fetch();
    }
}
