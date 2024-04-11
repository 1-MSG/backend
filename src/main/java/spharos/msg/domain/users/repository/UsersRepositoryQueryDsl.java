package spharos.msg.domain.users.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.users.entity.Users;

public interface UsersRepositoryQueryDsl {
    List<Users> findUsersByPageable(Pageable pageable);
}
