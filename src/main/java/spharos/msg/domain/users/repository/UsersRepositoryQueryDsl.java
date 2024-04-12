package spharos.msg.domain.users.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.orders.dto.OrderResponse.OrderUserDto;
import spharos.msg.domain.users.entity.Users;

public interface UsersRepositoryQueryDsl {

    List<Users> findUsersByPageable(Pageable pageable);

    Optional<OrderUserDto> findOrderUserDtoByUuid(String uuid);
}
