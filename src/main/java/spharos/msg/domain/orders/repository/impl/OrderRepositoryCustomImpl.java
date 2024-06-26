package spharos.msg.domain.orders.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spharos.msg.domain.orders.dto.OrderResponse.OrderHistoryDto;
import spharos.msg.domain.orders.dto.QOrderResponse_OrderHistoryDto;
import spharos.msg.domain.orders.entity.QOrders;
import spharos.msg.domain.orders.repository.OrderRepositoryCustom;

@Component
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderHistoryDto> findAllByUserId(Long userId) {
        QOrders orders = QOrders.orders;
        return jpaQueryFactory
            .select(toOrderHistoryDto(orders))
            .from(orders)
            .where(orders.userId.eq(userId))
            .orderBy(orders.createdAt.desc())
            .distinct()
            .fetch();
    }

    private QOrderResponse_OrderHistoryDto toOrderHistoryDto(QOrders orders) {
        return new QOrderResponse_OrderHistoryDto(
            orders.id,
            orders.totalPrice,
            orders.createdAt
        );
    }
}
