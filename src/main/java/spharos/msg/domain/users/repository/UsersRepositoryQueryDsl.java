package spharos.msg.domain.users.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.admin.dto.AdminResponseDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderUserDto;

public interface UsersRepositoryQueryDsl {

    List<AdminResponseDto.SearchInfo> findUsersByPageable(Pageable pageable);

    List<AdminResponseDto.MonthlySignupCountV2> countByMonthly(LocalDateTime StartDate);

    Optional<OrderUserDto> findOrderUserDtoByUuid(String uuid);
}
