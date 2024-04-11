package spharos.msg.domain.users.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
}
