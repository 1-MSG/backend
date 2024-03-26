package spharos.msg.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.users.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}

