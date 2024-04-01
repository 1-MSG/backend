package spharos.msg.domain.users.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.users.entity.UserOAuthList;

@Repository
public interface UserOAuthListRepository extends JpaRepository<UserOAuthList, Long> {

    Boolean existsByUuid(String uuid);

    Optional<UserOAuthList> findByOAuthIdAndOAuthName(String OAuthId, String OAuthOAuthName);

    List<UserOAuthList> findByUuid(String uuid);
}
