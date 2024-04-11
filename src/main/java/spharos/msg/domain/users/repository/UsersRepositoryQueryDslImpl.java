package spharos.msg.domain.users.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.admin.dto.AdminResponseDto;
import spharos.msg.domain.admin.dto.QAdminResponseDto_SearchInfo;
import spharos.msg.domain.users.entity.QUsers;

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
}
