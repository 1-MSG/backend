package spharos.msg.domain.users.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.users.entity.KakaoUsers;

@Repository
public interface KakaoUsersRepository extends JpaRepository<KakaoUsers, Long> {

    Optional<KakaoUsers> findKakaoUsersByUserUuid(String userUuid);
}
