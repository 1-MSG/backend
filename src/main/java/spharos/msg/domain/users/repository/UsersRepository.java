package spharos.msg.domain.users.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.users.entity.UserStatus;
import spharos.msg.domain.users.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUuid(String uuid);

    Optional<Users> findByLoginId(String loginId);

    Optional<Users> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByLoginId(String loginId);

    void deleteByUuid(String uuid);

    long countByCreatedAtAfter(LocalDateTime dateTime);

    long countByStatus(UserStatus userStatus);

    List<Users> findByCreatedAtGreaterThanEqual(LocalDateTime dateTime);
}
