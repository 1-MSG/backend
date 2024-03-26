package spharos.msg.domain.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.orders.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
