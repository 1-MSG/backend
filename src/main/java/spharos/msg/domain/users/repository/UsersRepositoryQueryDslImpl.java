package spharos.msg.domain.users.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.orders.dto.OrderResponse.OrderUserDto;
import spharos.msg.domain.orders.dto.QOrderResponse_OrderUserDto;
import spharos.msg.domain.users.entity.QUsers;
import spharos.msg.domain.users.entity.Users;

@RequiredArgsConstructor
public class UsersRepositoryQueryDslImpl implements UsersRepositoryQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Users> findUsersByPageable(Pageable pageable) {
        QUsers users = QUsers.users;
        return jpaQueryFactory
            .select(users)
            .from(users)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    @Override
    public Optional<OrderUserDto> findOrderUserDtoByUuid(String uuid) {
        QUsers users = QUsers.users;
        OrderUserDto orderUserDto = jpaQueryFactory
            .select(toOrderUserDto(users))
            .from(users)
            .where(users.uuid.eq(uuid))
            .fetchOne();

        return Optional.ofNullable(orderUserDto);
    }

    private QOrderResponse_OrderUserDto toOrderUserDto(QUsers users) {
        return new QOrderResponse_OrderUserDto(
            users.loginId,
            users.userName,
            users.address,
            users.phoneNumber,
            users.email
        );
    }
}
