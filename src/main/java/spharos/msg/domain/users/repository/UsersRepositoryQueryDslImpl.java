package spharos.msg.domain.users.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.admin.dto.AdminResponseDto;
import spharos.msg.domain.admin.dto.AdminResponseDto.MonthlySignupCountV2;
import spharos.msg.domain.admin.dto.QAdminResponseDto_MonthlySignupCountV2;
import spharos.msg.domain.admin.dto.QAdminResponseDto_SearchInfo;
import spharos.msg.domain.orders.dto.OrderResponse.OrderUserDto;
import spharos.msg.domain.users.entity.QUsers;
import spharos.msg.domain.orders.dto.QOrderResponse_OrderUserDto;

@RequiredArgsConstructor
public class UsersRepositoryQueryDslImpl implements UsersRepositoryQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AdminResponseDto.SearchInfo> findUsersByPageable(Pageable pageable) {
        QUsers users = QUsers.users;
        return jpaQueryFactory
                .select(new QAdminResponseDto_SearchInfo(users.id, users.userName, users.email, users.status, users.uuid))
                .from(users)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<MonthlySignupCountV2> countByMonthly(LocalDateTime StartDate) {
        QUsers users = QUsers.users;

        return jpaQueryFactory
                .select(new QAdminResponseDto_MonthlySignupCountV2(
                        users.createdAt.year(), users.createdAt.month(), users.count()))
                .from(users)
                .where(users.createdAt.after(StartDate))
                .groupBy(users.createdAt.year(), users.createdAt.month())
                .orderBy(users.createdAt.year().asc(), users.createdAt.month().asc())
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
