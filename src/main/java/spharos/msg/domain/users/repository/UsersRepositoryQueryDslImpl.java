package spharos.msg.domain.users.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.admin.dto.AdminResponseDto;
import spharos.msg.domain.admin.dto.AdminResponseDto.MonthlySignupCount;
import spharos.msg.domain.admin.dto.QAdminResponseDto_MonthlySignupCount;
import spharos.msg.domain.admin.dto.QAdminResponseDto_SearchInfo;
import spharos.msg.domain.users.entity.QUsers;

@RequiredArgsConstructor
@Slf4j
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
    public List<List<MonthlySignupCount>> CountByMonthly() {
        QUsers users = QUsers.users;

        LocalDateTime targetDate = LocalDateTime
                .of(LocalDateTime.now().getYear() - 1, Month.JANUARY, 1, 0, 0);

        List<MonthlySignupCount> fetch = jpaQueryFactory
                .select(new QAdminResponseDto_MonthlySignupCount(
                        users.createdAt.year(), users.createdAt.month(), users.count()))
                .from(users)
                .where(users.createdAt.after(targetDate))
                .groupBy(users.createdAt.year(), users.createdAt.month())
                .fetch();
        for (MonthlySignupCount monthlySignupCount : fetch) {
//            log.info("", monthlySignupCount.getYear(), monthlySignupCount.getMonth(), monthlySignupCount.g());
        }
    }
}
