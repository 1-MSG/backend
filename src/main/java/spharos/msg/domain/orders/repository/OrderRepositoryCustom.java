package spharos.msg.domain.orders.repository;

import static spharos.msg.domain.orders.dto.OrderResponse.OrderHistoryDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderHistoryDto> findAllByUserId(Long userId);
}
