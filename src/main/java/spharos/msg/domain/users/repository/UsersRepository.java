package spharos.msg.domain.users.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.users.entity.UserStatus;
import spharos.msg.domain.users.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUuid(String uuid);

    Optional<Users> findByLoginId(String loginId);

    @Query("SELECT u.status FROM Users u WHERE u.loginId = :loginId")
    Optional<UserStatus> findStatusByLoginId(@Param("loginId") String loginId);

    Optional<Users> findByEmail(String email);

    @Query("SELECT u.loginId From Users u WHERE u.email = :email")
    Optional<String> findLoginIdByEmail(@Param("email") String email);

    Boolean existsByEmail(String email);

    Boolean existsByLoginId(String loginId);

    void deleteByUuid(String uuid);

    long countByCreatedAtAfter(LocalDateTime dateTime);

    long countByStatus(UserStatus userStatus);

    List<Users> findByCreatedAtGreaterThanEqual(LocalDateTime dateTime);
}
