package spharos.msg.domain.orders.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spharos.msg.domain.orders.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderRepositoryCustom {

    @Query("select o.id from Orders o")
    List<Long> findAllId();
}
