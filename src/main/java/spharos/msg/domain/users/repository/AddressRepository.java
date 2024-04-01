package spharos.msg.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.users.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUsersId(Long usersId);

    Optional<Address> findByUsersIdAndId(Long usersId, Long Id);
}

